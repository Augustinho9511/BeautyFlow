package beautyflow.com.br.service;

import beautyflow.com.br.model.entity.Produto;
import beautyflow.com.br.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto salvar(Produto produto) {

        if (produto.getQuantidadeAtual() > 0 && produto.getPrecoCustoTotal() != null) {
            BigDecimal custoCalculado = produto.getPrecoCustoTotal()
                    .divide(BigDecimal.valueOf(produto.getQuantidadeAtual()), 2, RoundingMode.HALF_UP);
            produto.setCustoPorMedida(custoCalculado);
        } else {
            produto.setCustoPorMedida(BigDecimal.ZERO);
        }

        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }
}
