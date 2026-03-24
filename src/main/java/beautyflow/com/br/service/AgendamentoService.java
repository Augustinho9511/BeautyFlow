package beautyflow.com.br.service;

import beautyflow.com.br.exception.RegraDeNegocioException;
import beautyflow.com.br.exception.ValidacaoException;
import beautyflow.com.br.model.dto.ComissaoProfissionalDTO;
import beautyflow.com.br.model.dto.DadosAgendamento;
import beautyflow.com.br.model.dto.DadosDetalhamentoAgendamento;
import beautyflow.com.br.model.dto.FinanceiroResumoDTO;
import beautyflow.com.br.model.entity.*;
import beautyflow.com.br.model.enums.StatusAgendamento;
import beautyflow.com.br.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              FichaTecnicaRepository fichaTecnicaRepository,
                              ProdutoRepository produtoRepository,
                              ServicoRepository servicoRepository,
                              ClienteRepository clienteRepository,
                              ProfissionalRepository profissionalRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.produtoRepository = produtoRepository;
        this.servicoRepository = servicoRepository;
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public Page<DadosDetalhamentoAgendamento> listarTodosPaginado(Pageable paginacao) {
        return agendamentoRepository.findAll(paginacao).map(DadosDetalhamentoAgendamento::new);
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public List<ComissaoProfissionalDTO> listarComissoes() {
        return agendamentoRepository.calcularComissoes();
    }

    @Transactional
    public DadosDetalhamentoAgendamento salvar(DadosAgendamento dados) {

        var cliente = clienteRepository.findById(dados.idCliente())
                .filter(Cliente::getAtivo)
                .orElseThrow(() -> new ValidacaoException("Cliente não encontrado ou inativo."));

        var profissional = profissionalRepository.findById(dados.idProfissional())
                .filter(Profissional::getAtivo)
                .orElseThrow(() -> new ValidacaoException("Profissional não encontrado ou inativo."));

        var servico = servicoRepository.findById(dados.idServico())
                .orElseThrow(() -> new ValidacaoException("Serviço não encontrado."));

        if (dados.dataHoraInicio().isBefore(LocalDateTime.now())) {
            throw new ValidacaoException("A data do agendamento não pode ser no passado!");
        }

        var agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setProfissional(profissional);
        agendamento.setServico(servico);
        agendamento.setDataHoraInicio(dados.dataHoraInicio());
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        LocalDateTime inicio = agendamento.getDataHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(servico.getTempoEstimadoMinutos());
        agendamento.setDataHoraFim(fim);

        boolean temConflito = agendamentoRepository.existeConflitoDeHorario(
                profissional.getId(), inicio, fim);

        if (temConflito) {
            throw new ValidacaoException("A profissional já possui um agendamento neste horário!");
        }

        Agendamento salvo = agendamentoRepository.save(agendamento);

        if (StatusAgendamento.CONCLUIDO.equals(salvo.getStatus())) {
            BigDecimal custoTotal = calcularCustoEBaixarEstoque(servico);
            salvo.setLucroReal(servico.getPrecoCobrado().subtract(custoTotal));
            salvo = agendamentoRepository.save(salvo);
        }

        return new DadosDetalhamentoAgendamento(salvo);
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

    @Transactional
    public DadosDetalhamentoAgendamento cancelar(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Agendamento não encontrado."));

        if (StatusAgendamento.CANCELADO.equals(agendamento.getStatus())) {
            throw new ValidacaoException("Este agendamento já encontra-se cancelado.");
        }

        if (StatusAgendamento.CONCLUIDO.equals(agendamento.getStatus())) {
            devolverAoEstoque(agendamento.getServico());
            agendamento.setLucroReal(BigDecimal.ZERO);
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);

        return new DadosDetalhamentoAgendamento(agendamento);
    }

    private void devolverAoEstoque(Servico servico) {
        List<FichaTecnica> itensFicha = fichaTecnicaRepository.findByServico(servico);

        for(FichaTecnica item : itensFicha) {
            Produto produto = item.getProduto();
            Double quantidadeDevolvida = item.getQuantidadeGasta();

            produto.setQuantidadeAtual(produto.getQuantidadeAtual() + quantidadeDevolvida);
            produtoRepository.save(produto);

            System.out.println("Estorno realisado: " + produto.getNome() + "| Devolvido: " + quantidadeDevolvida);
        }
    }

    @Transactional
    public DadosDetalhamentoAgendamento concluir(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Agendamento não encontrado."));

        if (!StatusAgendamento.AGENDADO.equals(agendamento.getStatus())) {
            throw new ValidacaoException("Apenas agendamentos com status AGENDADO podem ser concluídos.");
        }

        agendamento.setStatus(StatusAgendamento.CONCLUIDO);

        BigDecimal custoTotal = calcularCustoEBaixarEstoque(agendamento.getServico());
        agendamento.setLucroReal(agendamento.getServico().getPrecoCobrado().subtract(custoTotal));

        return new DadosDetalhamentoAgendamento(agendamento);
    }

    public List<DadosDetalhamentoAgendamento> listar(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null) {
            inicio = LocalDateTime.now().withHour(0).withMinute(0);
        }
        if (fim == null) {
            fim = inicio.plusDays(1).withHour(23).withMinute(59);
        }

        return agendamentoRepository.buscarPorPeriodo(inicio, fim)
                .stream()
                .map(DadosDetalhamentoAgendamento::new)
                .toList();
    }

}