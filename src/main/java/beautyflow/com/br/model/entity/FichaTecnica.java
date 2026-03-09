package beautyflow.com.br.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Entity
@Getter
@Setter
@Table(name = "tb_ficha_tecnica")
@NoArgsConstructor
@AllArgsConstructor
public class FichaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name = "servico_id")
    private Servico servicoId;

    @ManyToMany
    @JoinColumn(name = "produto_id")
    private Produto produtoId;

    @NotNull
    @Positive
    private Double quantidadeGasta;

}
