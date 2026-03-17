package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.DadosAtualizacaoCliente;
import beautyflow.com.br.model.dto.DadosDetalhamentoCliente;
import beautyflow.com.br.model.entity.Cliente;
import beautyflow.com.br.repository.ClienteRepository;
import beautyflow.com.br.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRepository repository;

    public ClienteController(ClienteService clienteService, ClienteRepository repository) {
        this.clienteService = clienteService;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@Valid @RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoCliente dados) {
        var cliente = repository.getReferenceById(dados.id());
        cliente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoCliente(cliente));
    }
}
