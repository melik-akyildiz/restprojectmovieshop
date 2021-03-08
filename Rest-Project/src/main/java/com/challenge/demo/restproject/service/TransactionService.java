package com.challenge.demo.restproject.service;

import com.challenge.demo.restproject.entity.TransactionHistory;
import com.challenge.demo.restproject.entity.User;
import com.challenge.demo.restproject.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository accountsRepository) {
        this.transactionRepository = accountsRepository;
    }


    public TransactionHistory createTransaction(TransactionHistory transactionHistory) {
        return transactionRepository.save(transactionHistory);
    }


    public List<TransactionHistory> allTransactionsByUser(User user) {
        return transactionRepository.findAllByUser(user);
    }

    public TransactionHistory findRecordById(long id) {
        return transactionRepository.findById(id).get();
    }

}
