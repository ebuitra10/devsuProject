package com.devsu.project.msvc_cuentas.repositories.Jpa;

import com.devsu.project.msvc_cuentas.domain.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovementJpaRepository extends JpaRepository<MovementEntity, Long> {

    List<MovementEntity> findByAccount_Id(Long accountId);

    @Query("SELECT m FROM MovementEntity m WHERE m.account.clientId = :clientId " +
            "AND m.date BETWEEN :startDate AND :endDate")
    List<MovementEntity> findByClientIdAndDateBetween(
            @Param("clientId") Long clientId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
