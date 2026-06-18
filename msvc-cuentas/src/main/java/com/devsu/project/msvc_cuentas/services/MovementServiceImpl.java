package com.devsu.project.msvc_cuentas.services;

import com.devsu.project.msvc_cuentas.domain.AccountEntity;
import com.devsu.project.msvc_cuentas.domain.MovementEntity;
import com.devsu.project.msvc_cuentas.domain.dto.AccountStatementDto;
import com.devsu.project.msvc_cuentas.repositories.IAccountRepository;
import com.devsu.project.msvc_cuentas.repositories.IMovementRepository;
import com.devsu.project.msvc_cuentas.services.usecase.IMovementUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements IMovementUseCase {

    private final IMovementRepository movementRepository;
    private final IAccountRepository accountRepository;

    @Override
    public List<MovementEntity> findAll() {
        return movementRepository.findAll();
    }

    @Override
    public Optional<MovementEntity> findById(Long id) {
        return movementRepository.findById(id);
    }

    @Override
    public List<MovementEntity> findByAccountId(Long accountId) {
        return movementRepository.findByAccountId(accountId);
    }

    @Override
    public MovementEntity save(Long accountId, MovementEntity movement) {
        // F2: Buscar la cuenta
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        // F2: Calcular saldo disponible
        double currentBalance = account.getInitialBalance();
        double movementValue = movement.getValue();
        double newBalance = currentBalance + movementValue;

        // F3: Validar saldo disponible
        if (newBalance < 0) {
            throw new IllegalArgumentException("Saldo no disponible");
        }

        // F2: Actualizar saldo de la cuenta
        account.setInitialBalance(newBalance);
        accountRepository.save(account);

        // F2: Registrar el movimiento
        movement.setAccount(account);
        movement.setBalance(newBalance);

        // F2: Determinar tipo de movimiento automaticamente
        if (movementValue > 0) {
            movement.setMovementType("Deposito");
        } else {
            movement.setMovementType("Retiro");
        }

        return movementRepository.save(movement);
    }

    @Override
    public MovementEntity update(Long id, MovementEntity movement) {
        MovementEntity existing = movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movement not found with id: " + id));

        existing.setMovementType(movement.getMovementType());
        existing.setValue(movement.getValue());
        existing.setBalance(movement.getBalance());

        return movementRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        movementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movement not found with id: " + id));
        movementRepository.deleteById(id);
    }

    @Override
    public List<AccountStatementDto> getAccountStatement(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        // F4: Obtener movimientos por cliente y rango de fechas
        List<MovementEntity> movements = movementRepository
                .findByClientIdAndDateBetween(clientId, startDate, endDate);

        if (movements.isEmpty()) {
            throw new RuntimeException("No movements found for client id: " + clientId);
        }

        // F4: Mapear a DTO con el formato que pide la prueba
        return movements.stream()
                .map(m -> AccountStatementDto.builder()
                        .date(m.getDate())
                        .clientName("Client " + clientId)
                        .accountNumber(m.getAccount().getAccountNumber())
                        .accountType(m.getAccount().getAccountType())
                        .initialBalance(m.getAccount().getInitialBalance())
                        .status(m.getAccount().getStatus())
                        .movementValue(m.getValue())
                        .availableBalance(m.getBalance())
                        .build())
                .collect(Collectors.toList());
    }
}
