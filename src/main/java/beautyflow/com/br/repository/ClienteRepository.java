package beautyflow.com.br.repository;

import beautyflow.com.br.model.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Page<Cliente> findAllByAtivoTrue(Pageable paginacao);

    Page<Cliente> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable paginacao);
}
