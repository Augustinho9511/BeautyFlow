package beautyflow.com.br.model.dto;

import beautyflow.com.br.model.entity.Produto;

import java.math.BigDecimal;

public record DadosDetalhamentoProduto(
        Long id,
        String nome,
        Double quantidadeAtual,
        BigDecimal custoPorMedida
) {
    public DadosDetalhamentoProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getQuantidadeAtual(), produto.getCustoPorMedida());
    }
}
