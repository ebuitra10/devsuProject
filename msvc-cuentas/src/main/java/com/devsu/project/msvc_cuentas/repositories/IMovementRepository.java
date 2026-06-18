package com.devsu.project.msvc_cuentas.repositories;

import com.devsu.project.msvc_cuentas.domain.MovementEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMovementRepository {

    List<MovementEntity> findAll();

    Optional<MovementEntity> findById(Long id);

    List<MovementEntity> findByAccountId(Long accountId);

    List<MovementEntity> findByClientIdAndDateBetween(Long clientId, LocalDateTime startDate, LocalDateTime endDate);

    MovementEntity save(MovementEntity movement);

    void deleteById(Long id);
}
