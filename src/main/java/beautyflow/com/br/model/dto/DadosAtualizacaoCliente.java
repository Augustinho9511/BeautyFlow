package beautyflow.com.br.model.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoCliente(
        @NotNull
        Long id,
        String nome,
        String telefoneWhatsapp,
        String observacoes) {
}
