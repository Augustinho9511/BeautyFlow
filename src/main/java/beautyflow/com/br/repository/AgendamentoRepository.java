package beautyflow.com.br.repository;

import beautyflow.com.br.model.dto.FinanceiroResumoDTO;
import beautyflow.com.br.model.entity.Agendamento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    @EntityGraph(attributePaths = {"cliente", "servico", "profissional"})
    Optional<Agendamento> findFullById(Long id);

    @Query("SELECT new beautyflow.com.br.model.dto.FinanceiroResumoDTO(" +
            "SUM(s.precoCobrado), SUM(a.lucroReal), COUNT(a)) " +
            "FROM Agendamento a JOIN a.servico s " +
            "WHERE a.status = 'CONCLUIDO' " +
            "AND a.dataHoraInicio BETWEEN :inicio AND :fim")
    FinanceiroResumoDTO buscarResumoFinanceiro(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT COUNT(a) > 0 FROM Agendamento a " +
            "WHERE a.profissional.id = :profissionalId " +
            "AND a.status != 'CANCELADO' " +
            "AND a.dataHoraInicio < :fim " +
            "AND a.dataHoraFim > :inicio")
    boolean existeConflitoDeHorario(Long profissionalId, LocalDateTime inicio, LocalDateTime fim);


}
