package beautyflow.com.br.repository;

import beautyflow.com.br.model.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    List<Profissional> findAllByAtivoTrue();
}
