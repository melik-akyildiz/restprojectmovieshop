package com.challenge.demo.restproject.web.controller;

import com.challenge.demo.restproject.converters.TransactionConverter;
import com.challenge.demo.restproject.service.AccountService;
import com.challenge.demo.restproject.service.ProductService;
import com.challenge.demo.restproject.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/api/web")
public class ApiController {
    private final AccountService accountService;
    private final ProductService productService;
    private final TransactionService transactionService;
    private final TransactionConverter transactionConverter;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ApiController(AccountService accountService,
                         ProductService productService,
                         TransactionService transactionService,
                         TransactionConverter transactionConverter) {
        this.accountService = accountService;
        this.productService = productService;
        this.transactionService = transactionService;
        this.transactionConverter = transactionConverter;
        objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY,true);

    }

    @GetMapping(value = "/index")
    public String homepage() {
        return "index";
    }


    @PostMapping("/rentProduct")
    public ModelAndView rentProduct(@RequestParam("productId") List<String> productId) throws JsonProcessingException {
        ModelAndView transactionModel = new ModelAndView();
        transactionModel.addObject("accountList", accountService.allAccountsByUserId());
        List<Long> productIdsToLong = productId.stream().map(Long::parseLong).collect(Collectors.toList());
        transactionModel.addObject("productListId", objectMapper.writeValueAsString(productService
                                                                .findProductsByIdIn(productIdsToLong)));
        transactionModel.addObject("productList", productService.findProductsByIdIn(productIdsToLong));
        transactionModel.addObject("currentDate", LocalDate.now());
        transactionModel.setViewName("rentProduct");
        return transactionModel;
    }

    @PostMapping("/rentBackProduct")
    public ModelAndView rentBackProduct(@RequestParam("transactionId") String transactionId) throws JsonProcessingException {
        ModelAndView transactionModel = new ModelAndView();
        transactionModel.addObject("transaction",transactionService
                                                            .findRecordById(Long.parseLong(transactionId)));
        transactionModel.addObject("transactionId",transactionId);
        transactionModel.addObject("productList",
                                   transactionConverter.convertToProduct(transactionService
                                                       .findRecordById(Long.parseLong(transactionId))
                                                       .getProducts()));
        transactionModel.addObject("currentDate", LocalDate.now());
        transactionModel.setViewName("rentBackProduct");
        return transactionModel;
    }


    @GetMapping(value = "/createAccount")
    public ModelAndView createAccount() {
        ModelAndView createAccount = new ModelAndView();
        createAccount.addObject("generatedAccountNumber", accountService.generateAccountNumber());
        createAccount.setViewName("createAccount");
        return createAccount;
    }


}
