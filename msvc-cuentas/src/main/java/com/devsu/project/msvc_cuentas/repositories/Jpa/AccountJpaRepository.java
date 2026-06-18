package com.devsu.project.msvc_cuentas.repositories.Jpa;

import com.devsu.project.msvc_cuentas.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    List<AccountEntity> findByClientId(Long clientId);

    boolean existsByAccountNumber(String accountNumber);
}