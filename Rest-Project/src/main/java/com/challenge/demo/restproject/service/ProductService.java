package com.challenge.demo.restproject.service;

import com.challenge.demo.restproject.entity.Product;
import com.challenge.demo.restproject.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }


    public List<Product> allAccounts() {
        return productRepository.findAll();
    }


    public List<String> distinctGenres() {
        return productRepository.findDistinctGenre();
    }

    public List<Product> allByGenres(String genre) {
        return productRepository.findAllByGenre(genre);
    }

    public List<Product> allProductBySearchName(String name) {
        return productRepository.findAllByNameContainingIgnoreCase(name);
    }

    public List<Product> findProductsByIdIn(List<Long> ids) {
        return productRepository.findAllById(ids);
    }
}
