package beautyflow.com.br.controller;


import beautyflow.com.br.model.dto.DadosCadastroFichaTecnica;
import beautyflow.com.br.model.entity.FichaTecnica;
import beautyflow.com.br.model.entity.Produto;
import beautyflow.com.br.model.entity.Servico;
import beautyflow.com.br.service.FichaTecnicaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fichas-tecnicas")
public class FichaTecnicaController {

    @Autowired
    private FichaTecnicaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid DadosCadastroFichaTecnica dados) {

        var servico = new Servico();
        servico.setId(dados.idServico());

        var produto = new Produto();
        produto.setId(dados.idProduto());

        var ficha = new FichaTecnica();
        ficha.setServico(servico);
        ficha.setProduto(produto);
        ficha.setQuantidadeGasta(dados.quantidadeGasta());

        FichaTecnica fichaSalva = service.salvar(ficha);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Ficha técnica cadastrada com sucesso!");
    }



}
