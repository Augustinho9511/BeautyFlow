package beautyflow.com.br.model.dto;

import beautyflow.com.br.model.entity.Servico;

import java.math.BigDecimal;

public record DadosDetalhamentoServico(Long id, String nome, BigDecimal precoCobrado, Integer tempoEstimadoMinutos) {

    public DadosDetalhamentoServico(Servico servico) {
        this(servico.getId(), servico.getNome(), servico.getPrecoCobrado(), servico.getTempoEstimadoMinutos());
    }
}
