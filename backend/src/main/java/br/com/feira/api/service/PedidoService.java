package br.com.feira.api.service;

import br.com.feira.api.domain.ItemPedido;
import br.com.feira.api.domain.Pedido;
import br.com.feira.api.domain.Produto;
import br.com.feira.api.domain.StatusPedido;
import br.com.feira.api.dto.ItemPedidoDTO;
import br.com.feira.api.dto.PedidoDTO;
import br.com.feira.api.exception.RecursoNaoEncontradoException;
import br.com.feira.api.exception.RegraNegocioException;
import br.com.feira.api.repository.PedidoRepository;
import br.com.feira.api.repository.ProdutoRepository;
import br.com.feira.api.repository.StatusPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final StatusPedidoRepository statusPedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, StatusPedidoRepository statusPedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.statusPedidoRepository = statusPedidoRepository;
        this.produtoRepository = produtoRepository;
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
        Pedido pedido = new Pedido();
        copiarDTOParaEntidade(dto, pedido);

        pedido = pedidoRepository.save(pedido);
        return converterParaDTO(pedido);
    }

    @Transactional
    public PedidoDTO atualizar(Long id, PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido", id));

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

        // Limpa itens antigos se for atualização
        entidade.getItens().clear();
        
        double valorTotal = 0.0;
        
        if (dto.getItens() != null) {
            for (ItemPedidoDTO itemDto : dto.getItens()) {
                Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                        .orElseThrow(() -> new RecursoNaoEncontradoException("Produto", itemDto.getProdutoId()));
                
                ItemPedido item = new ItemPedido(entidade, produto, itemDto.getQuantidade(), produto.getPreco());
                entidade.getItens().add(item);
                valorTotal += (produto.getPreco() * itemDto.getQuantidade());
            }
        }
        
        entidade.setValorTotal(valorTotal);
    }

    private PedidoDTO converterParaDTO(Pedido entity) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setStatusPedidoId(entity.getStatusPedido().getId());
        dto.setStatusPedidoNome(entity.getStatusPedido().getNome());
        dto.setValorTotal(entity.getValorTotal());
        
        List<ItemPedidoDTO> itensDto = entity.getItens().stream().map(item -> {
            ItemPedidoDTO itemDto = new ItemPedidoDTO();
            itemDto.setId(item.getId());
            itemDto.setProdutoId(item.getProduto().getId());
            itemDto.setProdutoNome(item.getProduto().getNome());
            itemDto.setQuantidade(item.getQuantidade());
            itemDto.setPrecoUnitario(item.getPrecoUnitario());
            return itemDto;
        }).collect(Collectors.toList());
        
        dto.setItens(itensDto);
        return dto;
    }
}
