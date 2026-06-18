package com.devsu.project.msvc_cuentas.services;

import com.devsu.project.msvc_cuentas.domain.AccountEntity;
import com.devsu.project.msvc_cuentas.repositories.IAccountRepository;
import com.devsu.project.msvc_cuentas.services.usecase.IAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountUseCase {

    private final IAccountRepository accountRepository;

    @Override
    public List<AccountEntity> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<AccountEntity> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<AccountEntity> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<AccountEntity> findByClientId(Long clientId) {
        return accountRepository.findByClientId(clientId);
    }

    @Override
    public AccountEntity save(AccountEntity account) {
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new IllegalArgumentException("Account number already exists: " + account.getAccountNumber());
        }
        return accountRepository.save(account);
    }

    @Override
    public AccountEntity update(Long id, AccountEntity account) {
        AccountEntity existing = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        existing.setAccountNumber(account.getAccountNumber());
        existing.setAccountType(account.getAccountType());
        existing.setInitialBalance(account.getInitialBalance());
        existing.setStatus(account.getStatus());
        existing.setClientId(account.getClientId());

        return accountRepository.save(existing);
    }

    @Override
    public AccountEntity partialUpdate(Long id, AccountEntity account) {
        AccountEntity existing = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));

        if (account.getAccountNumber() != null) existing.setAccountNumber(account.getAccountNumber());
        if (account.getAccountType() != null) existing.setAccountType(account.getAccountType());
        if (account.getInitialBalance() != null) existing.setInitialBalance(account.getInitialBalance());
        if (account.getStatus() != null) existing.setStatus(account.getStatus());
        if (account.getClientId() != null) existing.setClientId(account.getClientId());

        return accountRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        accountRepository.deleteById(id);
    }
}
