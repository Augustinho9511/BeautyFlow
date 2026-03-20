package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.DadosAtualizacaoCliente;
import beautyflow.com.br.model.dto.DadosDetalhamentoCliente;
import beautyflow.com.br.model.entity.Cliente;
import beautyflow.com.br.repository.ClienteRepository;
import beautyflow.com.br.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRepository repository;

    public ClienteController(ClienteService clienteService, ClienteRepository repository) {
        this.clienteService = clienteService;
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoCliente>> listar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        Page<Cliente> pagina;

        if (nome != null && !nome.isBlank()) {
            pagina = repository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome, paginacao);
        }
        else {
            pagina = repository.findAllByAtivoTrue(paginacao);
        }

        var paginaDto = pagina.map(DadosDetalhamentoCliente::new);

        return ResponseEntity.ok(paginaDto);
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@Valid @RequestBody Cliente cliente) {
        cliente.setAtivo(true);
        Cliente novoCliente = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoCliente dados) {
        var cliente = repository.getReferenceById(dados.id());
        cliente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var cliente = repository.getReferenceById(id);
        cliente.inativar();

        return ResponseEntity.noContent().build();
    }
}
