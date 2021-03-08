package com.challenge.demo.restproject.converters;


import com.challenge.demo.restproject.dto.AccountDto;
import com.challenge.demo.restproject.entity.Accounts;
import com.challenge.demo.restproject.service.AccountService;
import com.challenge.demo.restproject.service.UserService;
import com.challenge.demo.restproject.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountConverter {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public AccountConverter(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }


    public Accounts convertToAccount(AccountDto accountDto) throws RuntimeException {

        String currentUserName = Utils.getUserName();

        Accounts accounts = new Accounts();
        accounts.setName(accountDto.getName());
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setUser(userService.userByName(currentUserName));
        accounts.setAccountNumber(accountDto.getAccountNumber());
        accounts.setBalance(Double.valueOf(100));

        return accounts;
    }

    public Accounts convertToUpdateAccount(AccountDto accountDto) throws RuntimeException {

        Accounts accounts = accountService.accountById(Long.parseLong(accountDto.getId())).get();
        accounts.setName(accountDto.getName());
        accounts.setBalance(Double.valueOf(accountDto.getBalance()));
        accounts.setCreatedAt(LocalDateTime.now());

        return accounts;
    }


}
