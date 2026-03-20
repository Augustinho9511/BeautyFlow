package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.DadosAtualizacaoCliente;
import beautyflow.com.br.model.dto.DadosAtualizacaoProfissional;
import beautyflow.com.br.model.dto.DadosDetalhamentoProfissional;
import beautyflow.com.br.model.entity.Profissional;
import beautyflow.com.br.repository.ProfissionalRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalController(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoProfissional>> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        Page<Profissional> pagina;

        if (nome != null && !nome.isBlank()) {
            pagina =profissionalRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, paginacao);
        } else {
            pagina = profissionalRepository.findAllByAtivoTrue(paginacao);
        }

        var paginaDto = pagina.map(DadosDetalhamentoProfissional::new);

        return ResponseEntity.ok(paginaDto);
    }

    @PostMapping
    public ResponseEntity<Profissional> cadastrar(@RequestBody @Valid Profissional profissional) {
        Profissional salvo = profissionalRepository.save(profissional);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoProfissional dados) {
        var profissional = profissionalRepository.getReferenceById(dados.id());
        profissional.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoProfissional(profissional));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var profissional =  profissionalRepository.getReferenceById(id);
        profissional.inativar();
        return ResponseEntity.noContent().build();
    }
}
