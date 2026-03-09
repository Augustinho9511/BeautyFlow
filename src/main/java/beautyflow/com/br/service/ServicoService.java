package beautyflow.com.br.service;


import beautyflow.com.br.model.entity.Servico;
import beautyflow.com.br.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepositoy) {
        this.servicoRepository = servicoRepositoy;
    }

    public Servico salvar(Servico servico) {
        return servicoRepository.save(servico);
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }
}
