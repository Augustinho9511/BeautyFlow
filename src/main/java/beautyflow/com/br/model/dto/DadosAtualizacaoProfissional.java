package beautyflow.com.br.model.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProfissional(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String especialidade) {
}
