package br.com.feira.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;

public class PedidoDTO {

    private Long id;

    @NotBlank(message = "O nome do pedido é obrigatório")
    @Size(min = 3, max = 150, message = "O nome do pedido deve ter no mínimo 3 caracteres")
    private String nome;

    @NotNull(message = "O ID do status do pedido é obrigatório")
    private Long statusPedidoId;

    private String statusPedidoNome;

    private List<ItemPedidoDTO> itens = new ArrayList<>();

    private Double valorTotal;

    public PedidoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Long getStatusPedidoId() { return statusPedidoId; }
    public void setStatusPedidoId(Long statusPedidoId) { this.statusPedidoId = statusPedidoId; }

    public String getStatusPedidoNome() { return statusPedidoNome; }
    public void setStatusPedidoNome(String statusPedidoNome) { this.statusPedidoNome = statusPedidoNome; }

    public List<ItemPedidoDTO> getItens() { return itens; }
    public void setItens(List<ItemPedidoDTO> itens) { this.itens = itens; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
}
