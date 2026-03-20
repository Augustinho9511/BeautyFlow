package beautyflow.com.br.model.dto;

import beautyflow.com.br.model.entity.Agendamento;

import java.time.LocalDateTime;

public record DadosDetalhamentoAgendamento(
        Long id,
        String nomeCliente,
        String nomeProfissional,
        String nomeServico,
        LocalDateTime dataHoraInicio
) {

    public DadosDetalhamentoAgendamento(Agendamento agendamento) {
        this (
                agendamento.getId(),
                agendamento.getCliente().getNome(),
                agendamento.getProfissional().getNome(),
                agendamento.getServico().getNome(),
                agendamento.getDataHoraInicio()
        );
    }
}
