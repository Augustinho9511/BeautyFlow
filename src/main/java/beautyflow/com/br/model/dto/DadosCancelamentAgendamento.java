package beautyflow.com.br.model.dto;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentAgendamento(
        @NotNull
        Long idAgendamento,
        @NotNull
        String motivo) {
}
