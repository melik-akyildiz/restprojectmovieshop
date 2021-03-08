package com.challenge.demo.restproject.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TransactionDto {
    private String amount;
    private String ownerAccountNumber;
    private String rentTo;
    private String currentDate;
    private String productList;
}
