package beautyflow.com.br.repository;

import beautyflow.com.br.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE p.quantidadeAtual <= p.quantidadeMinima")
    List<Produto> buscarProdutosEmEstoqueCritico();

}
