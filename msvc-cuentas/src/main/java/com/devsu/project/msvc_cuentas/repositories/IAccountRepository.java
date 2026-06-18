package com.devsu.project.msvc_cuentas.repositories;

import com.devsu.project.msvc_cuentas.domain.AccountEntity;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository {

    List<AccountEntity> findAll();

    Optional<AccountEntity> findById(Long id);

    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    List<AccountEntity> findByClientId(Long clientId);

    AccountEntity save(AccountEntity account);

    void deleteById(Long id);

    boolean existsByAccountNumber(String accountNumber);
}