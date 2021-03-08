package com.challenge.demo.restproject.service;

import com.challenge.demo.restproject.entity.Accounts;
import com.challenge.demo.restproject.entity.User;
import com.challenge.demo.restproject.repo.AccountsRepository;
import com.challenge.demo.restproject.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class AccountService {
    private final AccountsRepository accountsRepository;
    private final UserService userService;

    @Autowired
    public AccountService(AccountsRepository accountsRepository,
                          UserService userService) {
        this.accountsRepository = accountsRepository;
        this.userService = userService;
    }

    public Optional<Accounts> accountById(Long id) {
        return accountsRepository.findById(id);
    }

    public List<Accounts> accountList() {
        return accountsRepository.findAll();
    }

    public String generateAccountNumber() {
        Random rand = new Random();
        int accountNum = rand.nextInt((999999999 - 100000000) + 1) + 100000000;
        return String.valueOf(accountNum);
    }

    public List<Accounts> allAccountsByUserId() {
        String currentUserName = Utils.getUserName();
        return accountsRepository.findAllAccountsByUser(userService.userByName(currentUserName));
    }

    public List<User> distinctAccounts() {
        return accountsRepository.findDistinctAccount();
    }


    public List<Accounts> allAccountsByUserName(String name) {
        return accountsRepository.findAllByUserUsername(name);
    }

    public List<Accounts> allAccountsByAccountNum(String num) {
        return accountsRepository.findAllByAccountNumberContainingIgnoreCase(num);
    }

    public Accounts accountByAccountNum(String num) {
        return accountsRepository.findAccountsByAccountNumber(num);
    }

    public List<Accounts> allAccounts() {
        return accountsRepository.findAll();
    }

    public Accounts createAccount(Accounts accounts) {
        return accountsRepository.save(accounts);
    }

    public Accounts updateAccount(Accounts accounts) {
        return accountsRepository.save(accounts);
    }

    public void deleteAccount(Long accountId) {
        accountsRepository.deleteById(accountId);
    }


}
