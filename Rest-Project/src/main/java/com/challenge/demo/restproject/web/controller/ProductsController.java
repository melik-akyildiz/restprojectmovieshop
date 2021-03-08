package com.challenge.demo.restproject.web.controller;

import com.challenge.demo.restproject.entity.Product;
import com.challenge.demo.restproject.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    private static final Logger log = LoggerFactory.getLogger(ProductsController.class);

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public List<Product> products() {
        log.info("process=get-Products");
        return productService.allAccounts();
    }

    @GetMapping("/comboboxdata")
    public List<String> productDistinctGenre() {
        return productService.distinctGenres();
    }

    @GetMapping("/distinctproduct/{genre}")
    public List<Product> filterAccountsByName(@PathVariable String genre) {
        return productService.allByGenres(genre);
    }

    @GetMapping("/search/{name}")
    public List<Product> filterProductByName(@PathVariable String name) {
        return productService.allProductBySearchName(name);
    }





}
