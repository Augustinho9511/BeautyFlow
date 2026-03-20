package beautyflow.com.br.repository;

import beautyflow.com.br.model.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    Page<Servico> findAllByAtivoTrue(Pageable paginacao);

    Page<Servico> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable paginacao);
}
