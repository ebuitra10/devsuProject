package com.devsu.project.msvc_cuentas.services.usecase;

import com.devsu.project.msvc_cuentas.domain.AccountEntity;

import java.util.List;
import java.util.Optional;

public interface IAccountUseCase {

    List<AccountEntity> findAll();

    Optional<AccountEntity> findById(Long id);

    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    List<AccountEntity> findByClientId(Long clientId);

    AccountEntity save(AccountEntity account);

    AccountEntity update(Long id, AccountEntity account);

    AccountEntity partialUpdate(Long id, AccountEntity account);

    void deleteById(Long id);
}