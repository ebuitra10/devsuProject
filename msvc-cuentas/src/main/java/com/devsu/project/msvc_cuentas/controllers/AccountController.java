package com.devsu.project.msvc_cuentas.controllers;

import com.devsu.project.msvc_cuentas.domain.AccountEntity;
import com.devsu.project.msvc_cuentas.services.usecase.IAccountUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountUseCase accountUseCase;

    @GetMapping
    public ResponseEntity<List<AccountEntity>> findAll() {
        return ResponseEntity.ok(accountUseCase.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountEntity> findById(@PathVariable Long id) {
        return accountUseCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AccountEntity> save(@Valid @RequestBody AccountEntity account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountUseCase.save(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountEntity> update(@PathVariable Long id,
                                                @Valid @RequestBody AccountEntity account) {
        return ResponseEntity.ok(accountUseCase.update(id, account));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountEntity> partialUpdate(@PathVariable Long id,
                                                       @RequestBody AccountEntity account) {
        return ResponseEntity.ok(accountUseCase.partialUpdate(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        accountUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
