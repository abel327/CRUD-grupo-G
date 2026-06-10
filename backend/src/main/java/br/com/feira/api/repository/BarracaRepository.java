package br.com.feira.api.repository;

import br.com.feira.api.domain.Barraca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarracaRepository extends JpaRepository<Barraca, Long> {
}
