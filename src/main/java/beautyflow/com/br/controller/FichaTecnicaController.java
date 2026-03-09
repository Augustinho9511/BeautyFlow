package beautyflow.com.br.controller;

import beautyflow.com.br.model.entity.FichaTecnica;
import beautyflow.com.br.service.FichaTecnicaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fichas-tecnicas")
public class FichaTecnicaController {

    private final FichaTecnicaService fichaTecnicaService;

    public FichaTecnicaController(FichaTecnicaService fichaTecnicaService) {
        this.fichaTecnicaService = fichaTecnicaService;
     }

    @PostMapping
    public ResponseEntity<FichaTecnica> cadastrar(@Valid @RequestBody FichaTecnica fichaTecnica) {
        FichaTecnica novaFicha = fichaTecnicaService.salvar(fichaTecnica);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFicha);
    }

    @GetMapping
    public ResponseEntity<List<FichaTecnica>> listarTodos() {
        return ResponseEntity.ok(fichaTecnicaService.listarTodas());
    }
}
