package beautyflow.com.br.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamento(
        @NotNull
        Long idCliente,

        @NotNull
        Long idProfissional,

        @NotNull
        Long idServico,

        @NotNull
        @Future(message = "A data do agendamento deve ser no futuro")
        LocalDateTime dataHoraInicio) {
}
