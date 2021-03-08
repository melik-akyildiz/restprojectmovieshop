package com.challenge.demo.restproject.repo;

import com.challenge.demo.restproject.entity.TransactionHistory;
import com.challenge.demo.restproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory, Long> {

    List<TransactionHistory> findAllByUser(User user);


}
