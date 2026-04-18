package feira.graspcrud.dto;

public class StatusPedidoRequest {
    private final String nome;

    public StatusPedidoRequest(String nome) { this.nome = nome; }
    public String getNome() { return nome; }
}