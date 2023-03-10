package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class TransactionResponse {
    String type;
    List<Transaction> prepaid;
    List<Transaction> postpaid;
}
