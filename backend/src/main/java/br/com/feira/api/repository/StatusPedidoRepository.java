package br.com.feira.api.repository;

import br.com.feira.api.domain.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Long> {
    boolean existsByNomeIgnoreCase(String nome);
    Optional<StatusPedido> findByNomeIgnoreCase(String nome);
}
