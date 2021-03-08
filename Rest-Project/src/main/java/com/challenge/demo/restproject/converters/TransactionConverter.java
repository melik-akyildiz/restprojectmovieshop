package com.challenge.demo.restproject.converters;


import static java.time.temporal.ChronoUnit.DAYS;

import com.challenge.demo.restproject.dto.TransactionDto;
import com.challenge.demo.restproject.entity.Accounts;
import com.challenge.demo.restproject.entity.Product;
import com.challenge.demo.restproject.entity.TransactionHistory;
import com.challenge.demo.restproject.service.AccountService;
import com.challenge.demo.restproject.service.ProductService;
import com.challenge.demo.restproject.service.UserService;
import com.challenge.demo.restproject.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionConverter {

    private final UserService userService;
    private final AccountService accountService;
    private final ProductService productService;

    @Autowired
    public TransactionConverter(UserService userService, AccountService accountService,
                                ProductService productService) {
        this.userService = userService;
        this.accountService = accountService;
        this.productService = productService;
    }


    public TransactionHistory convertToTransaction(
        TransactionDto transactionDto) throws RuntimeException {

        String currentUserName = Utils.getUserName();
        Accounts ownerAccount = accountService.accountByAccountNum(transactionDto.getOwnerAccountNumber());
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setOwnerAccount(accountService.accountByAccountNum(transactionDto.getOwnerAccountNumber()));
        transactionHistory.setName(ownerAccount.getName());
        transactionHistory.setUser(userService.userByName(currentUserName));

        Long bonus = ownerAccount.getBonus();
        transactionHistory = calculatePrice(transactionHistory, transactionDto);
        validate(transactionHistory);
        ownerAccount.setBonus(bonus + transactionHistory.getBonus());

        transactionHistory.setOwnerAccount(ownerAccount);
        transactionHistory.setProducts(transactionDto.getProductList());
        transactionHistory.setStatus("ACTIVE");

        return transactionHistory;
    }

    public List<Product> convertToProduct(String product){
        Gson gson = new GsonBuilder().create();
        JsonArray jsonArray = new JsonParser().parse(product).getAsJsonArray();
        List<Product> products = new ArrayList<>();
        jsonArray.forEach(node -> {
            JsonObject jsonObject = (JsonObject) node;
            products.add(gson.fromJson(jsonObject, Product.class));
        });
        return products;
    }

    private TransactionHistory calculatePrice(TransactionHistory transactionHistory, TransactionDto transactionDto) {
        Gson gson = new GsonBuilder().create();
        JsonArray jsonArray = new JsonParser().parse(transactionDto.getProductList()).getAsJsonArray();
        List<Product> products = new ArrayList<>();
        Long bonus = 0L;
        LocalDate currDate = LocalDate.now();
        transactionHistory.setCreatedAt(currDate);

        LocalDate rentDate = LocalDate.parse(transactionDto.getRentTo());
        transactionHistory.setRentTo(rentDate);
        long difference = DAYS.between(currDate, rentDate);

        jsonArray.forEach(node -> {
            JsonObject jsonObject = (JsonObject) node;
            products.add(gson.fromJson(jsonObject, Product.class));
        });

        Double totalPrice = 0.0;

        for (Product product : products) {
            Double productPrice = 0.0;
            if (difference > product.getProductsPrice().getInterval()) {
                productPrice = product.getProductsPrice().getAmount() +
                               ((difference - product.getProductsPrice().getInterval()) *
                                product.getProductsPrice().getAmount());
            } else {
                productPrice = product.getProductsPrice().getAmount();
            }
            bonus = bonus + product.getProductsPrice().getBonus();
            totalPrice = totalPrice + productPrice;
        }

        transactionHistory.setAmount(totalPrice);
        transactionHistory.setBonus(bonus);

        return transactionHistory;
    }

    private void validate(TransactionHistory transactionHistory) {
        if (DAYS.between(transactionHistory.getCreatedAt(), transactionHistory.getRentTo()) < 0) {
            throw new RuntimeException();
        }
    }
}
