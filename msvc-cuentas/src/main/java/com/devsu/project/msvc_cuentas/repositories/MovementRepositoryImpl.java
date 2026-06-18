package com.devsu.project.msvc_cuentas.repositories;

import com.devsu.project.msvc_cuentas.domain.MovementEntity;
import com.devsu.project.msvc_cuentas.repositories.Jpa.MovementJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovementRepositoryImpl implements IMovementRepository {

    private final MovementJpaRepository movementJpaRepository;

    @Override
    public List<MovementEntity> findAll() {
        return movementJpaRepository.findAll();
    }

    @Override
    public Optional<MovementEntity> findById(Long id) {
        return movementJpaRepository.findById(id);
    }

    @Override
    public List<MovementEntity> findByAccountId(Long accountId) {
        return movementJpaRepository.findByAccount_Id(accountId);
    }

    @Override
    public List<MovementEntity> findByClientIdAndDateBetween(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        return movementJpaRepository.findByClientIdAndDateBetween(clientId, startDate, endDate);
    }

    @Override
    public MovementEntity save(MovementEntity movement) {
        return movementJpaRepository.save(movement);
    }

    @Override
    public void deleteById(Long id) {
        movementJpaRepository.deleteById(id);
    }
}
