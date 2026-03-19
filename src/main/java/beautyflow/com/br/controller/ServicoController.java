package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.DadosAtualizacaoServico;
import beautyflow.com.br.model.dto.DadosDetalhamentoServico;
import beautyflow.com.br.model.entity.Servico;
import beautyflow.com.br.repository.ServicoRepository;
import beautyflow.com.br.service.ServicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;
    private final ServicoRepository servicoRepository;

    public ServicoController(ServicoService servicoService, ServicoRepository servicoRepository) {
        this.servicoService = servicoService;
        this.servicoRepository = servicoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid Servico servico) {
        servico.setAtivo(true);
        servicoRepository.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoServico(servico));
    }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoServico>> listarTodos() {
        var lista = servicoRepository.findAllByAtivoTrue().stream()
                .map(DadosDetalhamentoServico::new)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoServico dados) {
        var servico = servicoRepository.getReferenceById(dados.id());
        servico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoServico(servico));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var servico = servicoRepository.getReferenceById(id);
        servico.inativar();

        return ResponseEntity.noContent().build();
    }
}
