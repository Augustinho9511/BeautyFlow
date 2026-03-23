package beautyflow.com.br.controller;

import beautyflow.com.br.exception.RegraDeNegocioException;
import beautyflow.com.br.model.dto.DadosCadastroFichaTecnica;
import beautyflow.com.br.model.entity.FichaTecnica;
import beautyflow.com.br.repository.FichaTecnicaRepository;
import beautyflow.com.br.repository.ProdutoRepository;
import beautyflow.com.br.repository.ServicoRepository;
import beautyflow.com.br.service.FichaTecnicaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fichas-tecnicas")
public class FichaTecnicaController {

    @Autowired
    private FichaTecnicaRepository fichaTecnicaRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroFichaTecnica dados) {
        var servico = servicoRepository.findById(dados.idServico())
                .orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado"));

        var produto = produtoRepository.findById(dados.idProduto())
                .orElseThrow(() -> new RegraDeNegocioException("Produto não encontrado"));

        var ficha = new FichaTecnica();
        ficha.setServico(servico);
        ficha.setProduto(produto);
        ficha.setQuantidadeGasta(dados.quantidadeGasta());

        fichaTecnicaRepository.save(ficha);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Ficha técnica cadastrada! Serviço: " + servico.getNome() + " gastará " + dados.quantidadeGasta() + "de " + produto.getNome());

    }



}
