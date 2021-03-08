package com.challenge.demo.restproject.repo;

import com.challenge.demo.restproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("SELECT DISTINCT p.genre FROM Product p")
    List<String> findDistinctGenre();

    List<Product> findAllByGenre(String name);

    List<Product> findAllByNameContainingIgnoreCase(String name);



}
