package beautyflow.com.br.repository;

import beautyflow.com.br.model.entity.FichaTecnica;
import beautyflow.com.br.model.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {

    List<FichaTecnica> findByServico(Servico servico);
}
