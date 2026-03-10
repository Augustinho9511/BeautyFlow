package beautyflow.com.br.service;

import beautyflow.com.br.exception.RegraDeNegocioException;
import beautyflow.com.br.model.dto.FinanceiroResumoDTO;
import beautyflow.com.br.model.entity.Agendamento;
import beautyflow.com.br.model.entity.FichaTecnica;
import beautyflow.com.br.model.entity.Produto;
import beautyflow.com.br.model.entity.Servico;
import beautyflow.com.br.model.enums.StatusAgendamento;
import beautyflow.com.br.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final FichaTecnicaRepository fichaTecnicaRepository;
    private final ProdutoRepository produtoRepository;
    private final ServicoRepository servicoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              FichaTecnicaRepository fichaTecnicaRepository,
                              ProdutoRepository produtoRepository,
                              ServicoRepository servicoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.produtoRepository = produtoRepository;
        this.servicoRepository = servicoRepository;
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    @Transactional
    public Agendamento salvar(Agendamento agendamento) {
        Agendamento salvo = agendamentoRepository.save(agendamento);

        if (StatusAgendamento.CONCLUIDO.equals(salvo.getStatus())) {
            Servico servico = servicoRepository.findById(salvo.getServico().getId())
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

            BigDecimal custoTotal = calcularCustoEBaixarEstoque(servico);
            salvo.setLucroReal(servico.getPrecoCobrado().subtract(custoTotal));
            salvo = agendamentoRepository.save(salvo);
        }

        agendamentoRepository.flush(); // Garante que tudo foi pro banco
        entityManager.refresh(salvo);  // FORÇA o Java a reler os nomes (Cliente, Serviço) do banco

        return salvo;
    }

    private BigDecimal calcularCustoEBaixarEstoque(Servico servico) {
        List<FichaTecnica> itensFicha = fichaTecnicaRepository.findByServico(servico);
        BigDecimal custoAcumulado = BigDecimal.ZERO;

        for (FichaTecnica item : itensFicha) {
            Produto produto = item.getProduto();
            Double gasto = item.getQuantidadeGasta();

            if (produto.getQuantidadeAtual() < gasto) {
                throw new RegraDeNegocioException("Estoque insuficiente: " + produto.getNome());
            }

            produto.setQuantidadeAtual(produto.getQuantidadeAtual() - gasto);

            BigDecimal custoItem = BigDecimal.valueOf(gasto).multiply(produto.getCustoPorMedida());
            custoAcumulado = custoAcumulado.add(custoItem);

            produtoRepository.save(produto);
            System.out.println("Baixa: " + produto.getNome() + " | Custo: R$" + custoItem);
        }
        return custoAcumulado;
    }

    public FinanceiroResumoDTO obterResumo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentoRepository.buscarResumoFinanceiro(inicio, fim);
    }
}