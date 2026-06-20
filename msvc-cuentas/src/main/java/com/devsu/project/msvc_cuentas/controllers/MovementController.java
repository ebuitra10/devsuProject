package com.devsu.project.msvc_cuentas.controllers;

import com.devsu.project.msvc_cuentas.domain.MovementEntity;
import com.devsu.project.msvc_cuentas.domain.dto.AccountStatementDto;
import com.devsu.project.msvc_cuentas.services.usecase.IMovementUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovementController {

    private final IMovementUseCase movementUseCase;

    @GetMapping("/movimientos")
    public ResponseEntity<List<MovementEntity>> findAll() {
        return ResponseEntity.ok(movementUseCase.findAll());
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<MovementEntity> findById(@PathVariable Long id) {
        return movementUseCase.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/movimientos/cuenta/{accountId}")
    public ResponseEntity<List<MovementEntity>> findByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(movementUseCase.findByAccountId(accountId));
    }

    @PostMapping("/movimientos/deposito/{accountId}")
    public ResponseEntity<MovementEntity> saveDeposit(@PathVariable Long accountId,
                                               @Valid @RequestBody MovementEntity movement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementUseCase.saveDeposit(accountId, movement));
    }

    @PostMapping("/movimientos/retiro/{accountId}")
    public ResponseEntity<MovementEntity> saveWithdrawal(@PathVariable Long accountId,
                                               @Valid @RequestBody MovementEntity movement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movementUseCase.saveWithdrawal(accountId, movement));
    }

    @PutMapping("/movimientos/{id}")
    public ResponseEntity<MovementEntity> update(@PathVariable Long id,
                                                 @Valid @RequestBody MovementEntity movement) {
        return ResponseEntity.ok(movementUseCase.update(id, movement));
    }

    @DeleteMapping("/movimientos/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        movementUseCase.deleteById(id);
        return ResponseEntity.noContent().build();

    }


    @GetMapping("/reportes")
    public ResponseEntity<List<AccountStatementDto>> getAccountStatement(
            @RequestParam Long clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return ResponseEntity.ok(movementUseCase.getAccountStatement(clientId, startDate, endDate));

    }
}
