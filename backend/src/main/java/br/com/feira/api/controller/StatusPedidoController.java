package br.com.feira.api.controller;

import br.com.feira.api.dto.StatusPedidoDTO;
import br.com.feira.api.service.StatusPedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status-pedido")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StatusPedidoController {

    private final StatusPedidoService service;

    public StatusPedidoController(StatusPedidoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<StatusPedidoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusPedidoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<StatusPedidoDTO> salvar(@RequestBody @Valid StatusPedidoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusPedidoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid StatusPedidoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
