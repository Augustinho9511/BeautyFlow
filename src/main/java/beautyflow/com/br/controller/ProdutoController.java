package beautyflow.com.br.controller;

import beautyflow.com.br.model.entity.Produto;
import beautyflow.com.br.repository.ProdutoRepository;
import beautyflow.com.br.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoService produtoService,ProdutoRepository produtoRepository) {
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(@Valid @RequestBody Produto produto) {
        Produto novoProduto = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/estoque-critico")
    public ResponseEntity<List<Produto>> listarEstoqueCritico() {
        List<Produto> produtosEmFalta = produtoRepository.findProdutosComEstoqueCritico();
        return ResponseEntity.ok(produtosEmFalta);
    }

}
