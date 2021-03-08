package com.challenge.demo.restproject.web.controller;

import com.challenge.demo.restproject.dto.AccountDto;
import com.challenge.demo.restproject.converters.AccountConverter;
import com.challenge.demo.restproject.entity.Accounts;
import com.challenge.demo.restproject.entity.User;
import com.challenge.demo.restproject.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    private static final Logger log = LoggerFactory.getLogger(AccountsController.class);

    private final AccountService accountService;
    private final AccountConverter accountConverter;

    @Autowired
    public AccountsController(AccountService AccountService, AccountConverter accountConverter) {
        this.accountService = AccountService;
        this.accountConverter = accountConverter;
    }

    @GetMapping("")
    public List<Accounts> accounts(Model model) {
        log.info("process=get-Accounts");
        return accountService.allAccounts();
    }


    @GetMapping("/comboboxdata")
    public List<String> accountDistinctNames() {
        return accountService.distinctAccounts().stream().map(User::getUsername).collect(Collectors.toList());
    }

    @GetMapping("/distinctaccount/{name}")
    public List<Accounts> filterAccountsByName(@PathVariable String name) {
        return accountService.allAccountsByUserName(name);
    }

    @GetMapping("/search/{num}")
    public List<Accounts> filterAccountsByNumber(@PathVariable String num) {
        return accountService.allAccountsByAccountNum(num);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accounts> accounts(@PathVariable Long id) {
        log.info("process=get-Account, Account_id={}", id);
        Optional<Accounts> Account = accountService.accountById(id);
        return Account.map(u -> ResponseEntity.ok(u))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public boolean createAccount(@RequestBody AccountDto accountDto) {
        log.info("process=create-Account, Account_name={}", accountDto.getName());
        try {
            accountService.createAccount(accountConverter.convertToAccount(accountDto));
            return true;

        } catch (Exception e) {

            return false;
        }

    }

    @PutMapping("/update")
    public boolean updateAccount(@RequestBody AccountDto accountDto) {
        log.info("process=update-Account, Account_id={}");
        try {
            accountService.updateAccount(accountConverter.convertToUpdateAccount(accountDto));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        log.info("process=delete-Account, Account_id={}", id);
        accountService.deleteAccount(id);
    }


}
