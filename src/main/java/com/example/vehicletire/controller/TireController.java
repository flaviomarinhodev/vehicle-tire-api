package com.example.vehicletire.controller;

import com.example.vehicletire.business.TireBusiness;
import com.example.vehicletire.entity.Tire;
import com.example.vehicletire.entity.TireStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tires")
public class TireController {

    private final TireBusiness tireBusiness;

    @Autowired
    public TireController(TireBusiness tireBusiness) {
        this.tireBusiness = tireBusiness;
    }

    @PostMapping
    public ResponseEntity<Tire> criarPneu(@RequestBody Tire tire) {
        Tire criado = tireBusiness.cadastrarNovoPneu(tire);
        return ResponseEntity.ok(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tire> atualizarPneu(@PathVariable Long id, @RequestBody Tire dadosAtualizados) {
        Tire atualizado = tireBusiness.atualizarPneu(id, dadosAtualizados);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPneu(@PathVariable Long id) {
        tireBusiness.removerPneu(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Tire>> listarDisponiveis() {
        return ResponseEntity.ok(tireBusiness.listarTodosDisponiveis());
    }

    @GetMapping("/em-uso")
    public ResponseEntity<List<Tire>> listarEmUso() {
        return ResponseEntity.ok(tireBusiness.listarTodosEmUso());
    }

    @GetMapping("/marca")
    public ResponseEntity<List<Tire>> buscarPorMarca(@RequestParam String marca) {
        return ResponseEntity.ok(tireBusiness.buscarPorMarca(marca));
    }

    @GetMapping("/marca-status")
    public ResponseEntity<List<Tire>> buscarPorMarcaEStatus(@RequestParam String marca, @RequestParam TireStatus status) {
        return ResponseEntity.ok(tireBusiness.buscarPorMarcaEStatus(marca, status));
    }

    @GetMapping("/pressao-entre")
    public ResponseEntity<List<Tire>> listarPressaoEntre(@RequestParam double min, @RequestParam double max) {
        return ResponseEntity.ok(tireBusiness.listarPressaoEntre(min, max));
    }

    @GetMapping("/pressao-baixa")
    public ResponseEntity<List<Tire>> listarComPressaoBaixa(@RequestParam double pressaoMinima) {
        return ResponseEntity.ok(tireBusiness.listarComPressaoBaixa(pressaoMinima));
    }

    @GetMapping("/numero-fogo/{numeroFogo}")
    public ResponseEntity<Tire> buscarPorNumeroFogo(@PathVariable String numeroFogo) {
        Optional<Tire> tire = tireBusiness.buscarPorNumeroFogo(numeroFogo);
        return tire.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/veiculos")
    public ResponseEntity<Tire> buscarPorIdComVeiculos(@PathVariable Long id) {
        Optional<Tire> tire = tireBusiness.buscarPorIdComVeiculos(id);
        return tire.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nao-atribuido")
    public ResponseEntity<List<Tire>> listarNaoAtribuidos() {
        return ResponseEntity.ok(tireBusiness.listarNaoAtribuidos());
    }

    @GetMapping("/contar/disponiveis")
    public ResponseEntity<Long> contarDisponiveis() {
        return ResponseEntity.ok(tireBusiness.contarDisponiveis());
    }

    @GetMapping("/contar/em-uso")
    public ResponseEntity<Long> contarEmUso() {
        return ResponseEntity.ok(tireBusiness.contarEmUso());
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<Tire>> listarOrdenadosPorMarcaENumero() {
        return ResponseEntity.ok(tireBusiness.listarOrdenadosPorMarcaENumero());
    }

    @PostMapping("/buscar-por-marcas")
    public ResponseEntity<List<Tire>> buscarPorMarcas(@RequestBody List<String> marcas) {
        return ResponseEntity.ok(tireBusiness.buscarPorMarcas(marcas));
    }
}
