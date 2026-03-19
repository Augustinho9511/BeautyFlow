package beautyflow.com.br.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.math.BigDecimal;

public record DadosAtualizacaoServico(
        @NotNull
        Long id,
        String nome,
        BigDecimal precoCobrado,
        Integer tempoEstimadoMinutos) {
}
