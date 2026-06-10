package br.com.feira.api.controller;

import br.com.feira.api.domain.Barraca;
import br.com.feira.api.service.BarracaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barracas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BarracaController {

    @Autowired
    private BarracaService barracaService;

    @GetMapping
    public ResponseEntity<List<Barraca>> getAllBarracas() {
        return ResponseEntity.ok(barracaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Barraca> getBarracaById(@PathVariable Long id) {
        return ResponseEntity.ok(barracaService.findById(id));
    }
}
