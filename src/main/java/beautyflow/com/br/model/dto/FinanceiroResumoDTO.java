package beautyflow.com.br.model.dto;

import java.math.BigDecimal;

public record FinanceiroResumoDTO(

        BigDecimal faturamentoBruto,
        BigDecimal lucroTotal,
        Long totalAgendamentos
) {}
