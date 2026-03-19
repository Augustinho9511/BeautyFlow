package beautyflow.com.br.model.entity;


import beautyflow.com.br.model.dto.DadosAtualizacaoProfissional;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_profissionais")
@NoArgsConstructor
@AllArgsConstructor
public class Profissional {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do profissional é obrigatório")
    private String nome;

    private String telefone;

    @NotBlank(message = "A especialidade é obrigatória (ex: Manicure, Cabeleireira)")
    private String especialidade;

    private Boolean ativo;

    public void inativar() {
        this.ativo = false;
    }

    public void atualizarInformacoes(DadosAtualizacaoProfissional dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.especialidade() != null) {
            this.especialidade = dados.especialidade();
        }
    }
}
