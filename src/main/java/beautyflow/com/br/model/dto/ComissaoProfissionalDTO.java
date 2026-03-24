package beautyflow.com.br.model.dto;

import java.math.BigDecimal;

public record ComissaoProfissionalDTO(
        String nomeProfissional,
        BigDecimal totalComissao) {
}
