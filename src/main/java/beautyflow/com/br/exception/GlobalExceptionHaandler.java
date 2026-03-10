package beautyflow.com.br.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHaandler extends RuntimeException {

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<Map<String, Object>> handleRegraDeNegocio(RegraDeNegocioException ex, HttpServletRequest request) {

        Map<String, Object> erroFormatado = new LinkedHashMap<>();
        erroFormatado.put("timestamp", LocalDateTime.now());
        erroFormatado.put("status", HttpStatus.BAD_REQUEST.value());
        erroFormatado.put("erro", "Regra de Negócio Violada");
        erroFormatado.put("mensagem", ex.getMessage());
        erroFormatado.put("caminho", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroFormatado);


    }

}
