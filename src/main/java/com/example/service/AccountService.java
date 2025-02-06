package com.example.service;

import com.example.repository.AccountRepository;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Account;

@Service
public class AccountService {
    private AccountRepository accRepo;

    public AccountService(AccountRepository accountRepository) {
        accRepo = accountRepository;
    }

    public Optional<Account> register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4 ||
            accRepo.findByUsername(account.getUsername()).isPresent()) {
            return null;
        }
        return Optional.of(accRepo.save(account));                
    }

    public Optional<Account> login(Account account) {
        Optional<Account> foundAccount = accRepo.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (foundAccount.isPresent()) {
            return foundAccount;
        } else {
            return null;
        }
    }

}
