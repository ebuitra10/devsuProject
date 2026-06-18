package com.devsu.project.msvc_cuentas.services.usecase;

import com.devsu.project.msvc_cuentas.domain.MovementEntity;
import com.devsu.project.msvc_cuentas.domain.dto.AccountStatementDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMovementUseCase {

    List<MovementEntity> findAll();

    Optional<MovementEntity> findById(Long id);

    List<MovementEntity> findByAccountId(Long accountId);

    MovementEntity save(Long accountId, MovementEntity movement);

    MovementEntity update(Long id, MovementEntity movement);

    void deleteById(Long id);

    List<AccountStatementDto> getAccountStatement(Long clientId, LocalDateTime startDate, LocalDateTime endDate);
}
