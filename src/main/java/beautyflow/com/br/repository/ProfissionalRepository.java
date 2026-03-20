package beautyflow.com.br.repository;

import beautyflow.com.br.model.entity.Profissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Page<Profissional> findAllByAtivoTrue(Pageable paginacao);

    Page<Profissional> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable paginacao);
}
