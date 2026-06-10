package br.com.feira.api.controller;

import br.com.feira.api.domain.Produto;
import br.com.feira.api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/ofertas")
    public ResponseEntity<List<Produto>> getOfertas() {
        return ResponseEntity.ok(produtoService.findOfertas());
    }

    @GetMapping("/barraca/{barracaId}")
    public ResponseEntity<List<Produto>> getProdutosByBarraca(@PathVariable Long barracaId) {
        return ResponseEntity.ok(produtoService.findByBarraca(barracaId));
    }
}
