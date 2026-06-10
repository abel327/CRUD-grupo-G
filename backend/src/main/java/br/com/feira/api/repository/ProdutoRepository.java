package br.com.feira.api.repository;

import br.com.feira.api.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByBarracaId(Long barracaId);
    List<Produto> findByEmOfertaTrue();
}
