package beautyflow.com.br.exception;


import beautyflow.com.br.model.dto.DadosErroValidacao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        List<DadosErroValidacao> erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new)
                .toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity tratarErroRegraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
