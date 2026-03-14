package com.bank.digital.banking.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type; //DEPOSIT, WITHDRAW

    private double amount;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


    public Transaction(String type, double amount, LocalDateTime timestamp,Account account){
        this.type = type;
        this.account = account;
        this.timestamp = timestamp;
        this.account = account;
    }


}
