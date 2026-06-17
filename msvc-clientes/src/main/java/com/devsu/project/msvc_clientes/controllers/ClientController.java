package com.devsu.project.msvc_clientes.controllers;

import com.devsu.project.msvc_clientes.domain.ClientEntity;
import com.devsu.project.msvc_clientes.services.usecase.IClientUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final IClientUseCase clientUseCase;

    @GetMapping
    public ResponseEntity<List<ClientEntity>> findAll() {

        return ResponseEntity.ok(clientUseCase.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> findById(@PathVariable Long id) {

        return clientUseCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientEntity> save(@Valid @RequestBody ClientEntity client) {

        return ResponseEntity.status(HttpStatus.CREATED).body(clientUseCase.save(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> update(@PathVariable Long id, @Valid @RequestBody ClientEntity client) {

        return ResponseEntity.ok(clientUseCase.update(id, client));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientEntity> partialUpdate(@PathVariable Long id, @RequestBody ClientEntity client) {

        return ResponseEntity.ok(clientUseCase.partialUpdate(id, client));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        clientUseCase.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}