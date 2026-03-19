package beautyflow.com.br.model.dto;

import beautyflow.com.br.model.entity.Profissional;

public record DadosDetalhamentoProfissional(Long id, String nome, String telefone, String especialicao) {

    public DadosDetalhamentoProfissional(Profissional profissional) {
        this(profissional.getId(), profissional.getNome(), profissional.getTelefone(), profissional.getEspecialidade());
    }
}
