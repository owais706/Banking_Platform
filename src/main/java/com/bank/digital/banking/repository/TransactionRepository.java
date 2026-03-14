package com.bank.digital.banking.repository;

import com.bank.digital.banking.entity.Account;
import com.bank.digital.banking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

}
