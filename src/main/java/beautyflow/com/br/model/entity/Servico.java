package beautyflow.com.br.model.entity;


import beautyflow.com.br.model.dto.DadosAtualizacaoServico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "tb_servicos")
@NoArgsConstructor
@AllArgsConstructor
public class Servico {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do serviço é obrigatório")
    private String nome;

    @NotNull
    @Positive(message = "O preço deve ser maior que zero")
    private BigDecimal precoCobrado;

    @NotNull
    @Positive(message = "O tempo estimado em minutos é obrigatório")
    private Integer tempoEstimadoMinutos;

    private Boolean ativo = true;

    public void inativar() {
        this.ativo = false;
    }

    public void atualizarInformacoes(DadosAtualizacaoServico dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.precoCobrado() != null) {
            this.precoCobrado = dados.precoCobrado();
        }
        if (dados.tempoEstimadoMinutos() != null) {
            this.tempoEstimadoMinutos = dados.tempoEstimadoMinutos();
        }
    }

}
