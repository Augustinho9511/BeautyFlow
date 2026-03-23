package beautyflow.com.br.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosCadastroProduto (
        @NotNull
        String nome,

        @NotNull
        Double quantidadeAtual,

        @NotNull
        BigDecimal custoPorMedida,

        @NotBlank
        String unidadeMedida,

        @NotNull
        BigDecimal precoCustoTotal,

        @NotNull
        Double quantidadeMinima

){

}
