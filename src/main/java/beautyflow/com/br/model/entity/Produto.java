package beautyflow.com.br.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "tb_produtos")
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotBlank(message = "A unidade de medida é obrigatória")
    private String unidadeMedida;

    @NotNull
    @PositiveOrZero
    private Double quantidadeAtual;

    @NotNull
    @PositiveOrZero
    private Double quantidadeMinima;

    @NotNull
    @PositiveOrZero
    private BigDecimal precoCustoTotal;


    private BigDecimal CustoPorMedida;


}
