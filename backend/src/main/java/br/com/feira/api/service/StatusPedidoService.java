package br.com.feira.api.service;

import br.com.feira.api.domain.StatusPedido;
import br.com.feira.api.dto.StatusPedidoDTO;
import br.com.feira.api.exception.RecursoNaoEncontradoException;
import br.com.feira.api.exception.RegraNegocioException;
import br.com.feira.api.repository.StatusPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusPedidoService {

    private final StatusPedidoRepository repository;

    public StatusPedidoService(StatusPedidoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<StatusPedidoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StatusPedidoDTO buscarPorId(Long id) {
        StatusPedido status = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Status de Pedido", id));
        return converterParaDTO(status);
    }

    @Transactional
    public StatusPedidoDTO salvar(StatusPedidoDTO dto) {
        if (repository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new RegraNegocioException("Já existe um Status de Pedido com este nome.");
        }

        StatusPedido status = new StatusPedido();
        status.setNome(dto.getNome());

        status = repository.save(status);
        return converterParaDTO(status);
    }

    @Transactional
    public StatusPedidoDTO atualizar(Long id, StatusPedidoDTO dto) {
        StatusPedido status = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Status de Pedido", id));

        if (!status.getNome().equalsIgnoreCase(dto.getNome()) && repository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new RegraNegocioException("Já existe um Status de Pedido com este nome.");
        }

        status.setNome(dto.getNome());

        status = repository.save(status);
        return converterParaDTO(status);
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Status de Pedido", id);
        }
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RegraNegocioException("Não é possível excluir o Status pois existem pedidos vinculados a ele.");
        }
    }

    private StatusPedidoDTO converterParaDTO(StatusPedido entity) {
        return new StatusPedidoDTO(entity.getId(), entity.getNome());
    }
}
