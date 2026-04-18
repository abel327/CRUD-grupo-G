package feira.graspcrud.domain;

import feira.graspcrud.exception.RegraNegocioException;

/**
 * Entidade de classificação de status para pedidos.
 */
public class StatusPedido {
    private Long id;
    private String nome;

    public StatusPedido(Long id, String nome) {
        this.id = id;
        this.nome = nome == null ? null : nome.trim();
    }

    /**
     * Valida regras de negócio da entidade: nome obrigatório e min 3 caracteres.
     */
    public void validar() {
        if (nome == null || nome.length() < 3) {
            throw new RegraNegocioException("Nome do status deve ter ao menos 3 caracteres.");
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
}