package beautyflow.com.br.repository;

import beautyflow.com.br.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findAllByAtivoTrue();
}
