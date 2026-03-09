package beautyflow.com.br.service;

import beautyflow.com.br.model.entity.FichaTecnica;
import beautyflow.com.br.model.entity.Produto;
import beautyflow.com.br.model.entity.Servico;
import beautyflow.com.br.repository.FichaTecnicaRepository;
import beautyflow.com.br.repository.ProdutoRepository;
import beautyflow.com.br.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FichaTecnicaService {

    private final FichaTecnicaRepository repository;
    private final ServicoRepository servicoRepository;
    private final ProdutoRepository produtoRepository;

    // O Spring vai injetar os 3 repositórios aqui automaticamente
    public FichaTecnicaService(FichaTecnicaRepository repository,
                               ServicoRepository servicoRepository,
                               ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.servicoRepository = servicoRepository;
        this.produtoRepository = produtoRepository;
    }

    public FichaTecnica salvar(FichaTecnica fichaTecnica) {
        Servico servicoCompleto = servicoRepository.findById(fichaTecnica.getServico().getId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado no banco!"));

        Produto produtoCompleto = produtoRepository.findById(fichaTecnica.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no banco!"));

        fichaTecnica.setServico(servicoCompleto);
        fichaTecnica.setProduto(produtoCompleto);
        
        return repository.save(fichaTecnica);
    }

    public List<FichaTecnica> listarTodas() {
        return repository.findAll();
    }
}
