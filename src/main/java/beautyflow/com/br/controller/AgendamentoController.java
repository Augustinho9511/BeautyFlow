package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.DadosAgendamento;
import beautyflow.com.br.model.dto.DadosDetalhamentoAgendamento;
import beautyflow.com.br.model.dto.FinanceiroResumoDTO;
import beautyflow.com.br.model.entity.Agendamento;
import beautyflow.com.br.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<DadosDetalhamentoAgendamento> cadastrar(@Valid @RequestBody DadosAgendamento dados) {
        DadosDetalhamentoAgendamento novoAgendamento = service.salvar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAgendamento);
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoAgendamento>> listarTodos(
            @PageableDefault(size = 10, sort = {"dataHoraInicio"}) Pageable paginacao) {

        Page<DadosDetalhamentoAgendamento> pagina = service.listarTodosPaginado(paginacao);
        return ResponseEntity.ok(pagina);
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
