package beautyflow.com.br.model.dto;

import beautyflow.com.br.model.entity.Cliente;

public record DadosDetalhamentoCliente(Long id, String nome, String telefoneWhatsapp, String observacoes) {

    public DadosDetalhamentoCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getTelefoneWhatsapp(), cliente.getObservacoes());
    }
}
