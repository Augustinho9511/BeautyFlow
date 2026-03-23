package beautyflow.com.br.model.dto;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroFichaTecnica(
        @NotNull
        Long idServico,

        @NotNull
        Long idProduto,

        @NotNull
        Double quantidadeGasta
) {
}
