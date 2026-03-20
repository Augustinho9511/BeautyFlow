package beautyflow.com.br.model.entity;


import beautyflow.com.br.model.enums.StatusAgendamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Agendamento")
@Getter
@Setter
@Table(name = "tb_agendamento")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento status;

    @NotNull
    private LocalDateTime dataHoraInicio;

    private BigDecimal lucroReal;

    private LocalDateTime dataHoraFim;

    private Boolean cancelado = false;

    public void cancelar() {
        this.cancelado = true;
    }
}
