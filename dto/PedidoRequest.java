package feira.graspcrud.dto;

public class PedidoRequest {
    private final String nome;
    private final Long statusPedidoId;

    public PedidoRequest(String nome, Long statusPedidoId) {
        this.nome = nome;
        this.statusPedidoId = statusPedidoId;
    }
    public String getNome() { return nome; }
    public Long getStatusPedidoId() { return statusPedidoId; }
}