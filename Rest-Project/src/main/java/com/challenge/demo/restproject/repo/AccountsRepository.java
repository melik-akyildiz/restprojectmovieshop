package com.challenge.demo.restproject.repo;

import com.challenge.demo.restproject.entity.Accounts;
import com.challenge.demo.restproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    @Query("SELECT DISTINCT p.user FROM Accounts p")
    List<User> findDistinctAccount();

    List<Accounts> findAllByUserUsername(String name);

    Accounts findAccountsByAccountNumber(String num);

    List<Accounts> findAllAccountsByUser(User user);

    List<Accounts> findAllByAccountNumberContainingIgnoreCase(String name);



}
