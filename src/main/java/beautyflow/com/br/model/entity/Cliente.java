package beautyflow.com.br.model.entity;


import beautyflow.com.br.model.dto.DadosAtualizacaoCliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.parameters.P;

@Entity
@Getter
@Setter
@Table(name = "tb_cliente")
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nome;

    @NotBlank(message = "O Whatsapp é obrigatório p[ara notificações")
    private String telefoneWhatsapp;

    private String observacoes;

    private Boolean ativo;

    public void inativar() {
        this.ativo = false;
    }

    public void atualizarInformacoes(DadosAtualizacaoCliente dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }

        if (dados.telefoneWhatsapp() != null) {
            this.telefoneWhatsapp = dados.telefoneWhatsapp();
        }

        if (dados.observacoes() != null) {
            this.observacoes = dados.observacoes();
        }
    }
}