package br.com.feira.api.service;

import br.com.feira.api.domain.Produto;
import br.com.feira.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findOfertas() {
        return produtoRepository.findByEmOfertaTrue();
    }

    public List<Produto> findByBarraca(Long barracaId) {
        return produtoRepository.findByBarracaId(barracaId);
    }
}
