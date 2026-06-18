package com.devsu.project.msvc_cuentas.repositories;

import com.devsu.project.msvc_cuentas.domain.AccountEntity;
import com.devsu.project.msvc_cuentas.repositories.Jpa.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements IAccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public List<AccountEntity> findAll() {
        return accountJpaRepository.findAll();
    }

    @Override
    public Optional<AccountEntity> findById(Long id) {
        return accountJpaRepository.findById(id);
    }

    @Override
    public Optional<AccountEntity> findByAccountNumber(String accountNumber) {
        return accountJpaRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<AccountEntity> findByClientId(Long clientId) {
        return accountJpaRepository.findByClientId(clientId);
    }

    @Override
    public AccountEntity save(AccountEntity account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
        accountJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return accountJpaRepository.existsByAccountNumber(accountNumber);
    }
}