package br.com.feira.api.service;

import br.com.feira.api.domain.Pedido;
import br.com.feira.api.domain.StatusPedido;
import br.com.feira.api.dto.PedidoDTO;
import br.com.feira.api.exception.RecursoNaoEncontradoException;
import br.com.feira.api.exception.RegraNegocioException;
import br.com.feira.api.repository.PedidoRepository;
import br.com.feira.api.repository.StatusPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final StatusPedidoRepository statusPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, StatusPedidoRepository statusPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.statusPedidoRepository = statusPedidoRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido", id));
        return converterParaDTO(pedido);
    }

    @Transactional
    public PedidoDTO salvar(PedidoDTO dto) {
        if (pedidoRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new RegraNegocioException("Já existe um Pedido com este nome.");
        }

        Pedido pedido = new Pedido();
        copiarDTOParaEntidade(dto, pedido);

        pedido = pedidoRepository.save(pedido);
        return converterParaDTO(pedido);
    }

    @Transactional
    public PedidoDTO atualizar(Long id, PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido", id));

        if (!pedido.getNome().equalsIgnoreCase(dto.getNome()) && pedidoRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new RegraNegocioException("Já existe um Pedido com este nome.");
        }

        copiarDTOParaEntidade(dto, pedido);

        pedido = pedidoRepository.save(pedido);
        return converterParaDTO(pedido);
    }

    @Transactional
    public void excluir(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Pedido", id);
        }
        pedidoRepository.deleteById(id);
    }

    private void copiarDTOParaEntidade(PedidoDTO dto, Pedido entidade) {
        entidade.setNome(dto.getNome());

        StatusPedido statusPedido = statusPedidoRepository.findById(dto.getStatusPedidoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Status de Pedido", dto.getStatusPedidoId()));
        entidade.setStatusPedido(statusPedido);
    }

    private PedidoDTO converterParaDTO(Pedido entity) {
        return new PedidoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getStatusPedido().getId(),
                entity.getStatusPedido().getNome()
        );
    }
}
