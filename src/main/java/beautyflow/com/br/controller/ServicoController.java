package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.DadosAtualizacaoServico;
import beautyflow.com.br.model.dto.DadosDetalhamentoServico;
import beautyflow.com.br.model.entity.Servico;
import beautyflow.com.br.repository.ServicoRepository;
import beautyflow.com.br.service.ServicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoServico>> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        Page<Servico> pagina;

        if (nome != null && !nome.isBlank()) {
            pagina = servicoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, paginacao);
        } else {
            pagina = servicoRepository.findAllByAtivoTrue(paginacao);
        }

        var paginaDto = pagina.map(DadosDetalhamentoServico::new);

        return ResponseEntity.ok(paginaDto);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid Servico servico) {
        servico.setAtivo(true);
        servicoRepository.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoServico(servico));
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
