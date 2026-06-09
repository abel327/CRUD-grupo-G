package br.com.feira.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StatusPedidoDTO {

    private Long id;

    @NotBlank(message = "O nome do status é obrigatório")
    @Size(min = 3, max = 50, message = "O nome do status deve ter no mínimo 3 caracteres")
    private String nome;

    public StatusPedidoDTO() {}

    public StatusPedidoDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
