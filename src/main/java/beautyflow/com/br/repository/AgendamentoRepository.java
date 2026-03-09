package beautyflow.com.br.repository;

import beautyflow.com.br.model.entity.Agendamento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @EntityGraph(attributePaths = {"cliente", "servico"})
    Optional<Agendamento> findFullById(Long id);
}
