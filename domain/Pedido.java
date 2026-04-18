package feira.graspcrud.domain;

import feira.graspcrud.exception.RegraNegocioException;

/**
 * Entidade de Pedido da feira.
 */
public class Pedido {
    private Long id;
    private String nome;
    private Long statusPedidoId;

    public Pedido(Long id, String nome, Long statusPedidoId) {
        this.id = id;
        this.nome = nome == null ? null : nome.trim();
        this.statusPedidoId = statusPedidoId;
    }

    /**
     * Valida regras de negócio da entidade.
     */
    public void validar() {
        if (nome == null || nome.length() < 3) {
            throw new RegraNegocioException("Nome do pedido deve ter ao menos 3 caracteres.");
        }
        if (statusPedidoId == null || statusPedidoId <= 0) {
            throw new RegraNegocioException("Status do pedido é obrigatório.");
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public Long getStatusPedidoId() { return statusPedidoId; }
}