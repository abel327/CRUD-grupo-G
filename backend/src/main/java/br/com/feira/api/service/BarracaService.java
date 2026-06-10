package br.com.feira.api.service;

import br.com.feira.api.domain.Barraca;
import br.com.feira.api.repository.BarracaRepository;
import br.com.feira.api.exception.RecursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarracaService {

    @Autowired
    private BarracaRepository barracaRepository;

    public List<Barraca> findAll() {
        return barracaRepository.findAll();
    }

    public Barraca findById(Long id) {
        return barracaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Barraca", id));
    }
}
