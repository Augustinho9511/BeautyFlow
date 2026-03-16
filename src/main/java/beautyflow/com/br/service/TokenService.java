package beautyflow.com.br.service;

import org.springframework.beans.factory.annotation.Value;
import beautyflow.com.br.exception.RegraDeNegocioException;
import beautyflow.com.br.model.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("API BeautyFlow")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);

        }catch (JWTCreationException exception) {
            throw new RegraDeNegocioException("Erro ao gerar token JWT: " + exception.getMessage());
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API BeautyFlow")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTCreationException exception) {
            throw new RegraDeNegocioException("Token JWT inválido ou expirado!");
        }
    }
}
