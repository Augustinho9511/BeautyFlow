package beautyflow.com.br.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
}