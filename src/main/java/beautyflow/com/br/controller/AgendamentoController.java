package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.*;
import beautyflow.com.br.model.entity.Agendamento;
import beautyflow.com.br.service.AgendamentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AgendamentoService service;


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
    public ResponseEntity<DadosDetalhamentoAgendamento> cancelarAgendamento(@PathVariable Long id) {
        var dto = service.cancelar(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/concluir")
    @Transactional
    public ResponseEntity<DadosDetalhamentoAgendamento> concluirAgendamento(@PathVariable Long id) {
        var dto = service.concluir(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<DadosDetalhamentoAgendamento>> listar(
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime fim) {
        var lista = service.listar(inicio, fim);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/comissoes")
    public ResponseEntity<List<ComissaoProfissionalDTO>> listarComissoes() {
        var comissoes = service.listarComissoes();
        return ResponseEntity.ok(comissoes);
    }

}
