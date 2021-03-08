package com.challenge.demo.restproject.web.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.challenge.demo.restproject.converters.TransactionConverter;
import com.challenge.demo.restproject.dto.TransactionDto;
import com.challenge.demo.restproject.entity.TransactionHistory;
import com.challenge.demo.restproject.service.AccountService;
import com.challenge.demo.restproject.service.TransactionService;
import com.challenge.demo.restproject.service.UserService;
import com.challenge.demo.restproject.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;
    private final TransactionConverter transactionConverter;
    private final AccountService accountService;
    private final UserService userService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 TransactionConverter transactionConverter,
                                 AccountService accountService,
                                 UserService userService) {

        this.transactionService = transactionService;
        this.transactionConverter = transactionConverter;
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<TransactionHistory> transactions(Model model) {
        log.info("process=get-Transactions");
        String currentUserName = Utils.getUserName();
        return transactionService.allTransactionsByUser(userService.userByName(currentUserName));
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public boolean createAccount(@RequestBody TransactionDto transactionDto) {
        log.info("process=Transaction-Account, Account_name={}", transactionDto.getOwnerAccountNumber());
        try {
            TransactionHistory transactionHistory = transactionConverter.convertToTransaction(transactionDto);
            accountService.updateAccount(transactionHistory.getOwnerAccount());
            transactionService.createTransaction(transactionHistory);
            return true;

        } catch (Exception e) {

            return false;
        }

    }

}
