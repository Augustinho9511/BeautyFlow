package beautyflow.com.br.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@Table(name = "tb_agendamento")
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name = "cliente_id")
    private Long clienteId;

    @ManyToMany
    @JoinColumn(name = "servico_id")
    private Long servicoId;

    @NotNull
    private LocalDateTime dataHoraInicio;

    @NotBlank
    private String status;

    private BigDecimal lucroReal;
}
