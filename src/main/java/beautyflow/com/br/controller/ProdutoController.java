package beautyflow.com.br.controller;

import beautyflow.com.br.model.dto.DadosCadastroProduto;
import beautyflow.com.br.model.dto.DadosDetalhamentoProduto;
import beautyflow.com.br.model.entity.Produto;
import beautyflow.com.br.repository.ProdutoRepository;
import beautyflow.com.br.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoProduto> cadastrar(@RequestBody @Valid DadosCadastroProduto dados) {
        var produto = new Produto();
        produto.setNome(dados.nome());
        produto.setQuantidadeAtual(dados.quantidadeAtual());
        produto.setUnidadeMedida(dados.unidadeMedida());
        produto.setPrecoCustoTotal(dados.precoCustoTotal());
        produto.setQuantidadeMinima(dados.quantidadeMinima());

        Produto produtoSalvo = service.salvar(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoProduto(produtoSalvo));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoProduto>> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var pagina = repository.findAll(paginacao).map(DadosDetalhamentoProduto::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/estoque-critico")
    public ResponseEntity<List<DadosDetalhamentoProduto>> listarEstoqueCritico() {
        var produtosCriticos = service.listarEstoqueCritico().stream()
                .map(DadosDetalhamentoProduto::new)
                .toList();

        return ResponseEntity.ok(produtosCriticos);
    }

}
