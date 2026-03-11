package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.FinanceiroResumoDTO;
import beautyflow.com.br.model.entity.Agendamento;
import beautyflow.com.br.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Agendamento> cadastrar(@Valid @RequestBody Agendamento agendamento) {
        Agendamento novoAgendamento = service.salvar(agendamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/resumo")
    public ResponseEntity<FinanceiroResumoDTO> getResumo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(service.obterResumo(inicio, fim));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Agendamento> cancelarAgendamento(@PathVariable Long id) {
        Agendamento cancelado = service.cancelar(id);
        return ResponseEntity.ok(cancelado);
    }
}
