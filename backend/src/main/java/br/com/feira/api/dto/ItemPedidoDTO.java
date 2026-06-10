package br.com.feira.api.dto;

import jakarta.validation.constraints.NotNull;

public class ItemPedidoDTO {

    private Long id;

    @NotNull(message = "O ID do produto é obrigatório")
    private Long produtoId;

    private String produtoNome;

    @NotNull(message = "A quantidade é obrigatória")
    private Integer quantidade;

    private Double precoUnitario;

    public ItemPedidoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }

    public String getProdutoNome() { return produtoNome; }
    public void setProdutoNome(String produtoNome) { this.produtoNome = produtoNome; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }
}
