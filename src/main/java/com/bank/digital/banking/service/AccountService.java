package com.bank.digital.banking.service;

import com.bank.digital.banking.entity.Account;
import com.bank.digital.banking.entity.Transaction;
import com.bank.digital.banking.repository.AccountRepository;
import com.bank.digital.banking.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,TransactionRepository transactionRepository){
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public Account getAccount(Long id){
        return accountRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Account not found"));
    }

    @Transactional
    public Account deposit(Long id, double amount){
        Account account = getAccount(id);
        account.setBalance(account.getBalance() + amount);

        Transaction tx = new Transaction ("DEPOSIT",amount, LocalDateTime.now(), account);
        transactionRepository.save(tx);

        return accountRepository.save(account);

    }

    @Transactional
    public Account withdraw(Long id, double amount){

        Account account = getAccount(id);

        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance()-amount);

        Transaction tx = new Transaction("WITHDRAW",amount,LocalDateTime.now(),account);
        transactionRepository.save(tx);
        return accountRepository.save(account);
    }

    @Transactional
    public void transfer(Long fromId, Long toId, double amount){
        Account from = getAccount(fromId);
        Account to = getAccount(toId);

        if(from.getBalance()<amount){
            throw new RuntimeException("Insufficient balance");

        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);

        accountRepository.save(to);

        transactionRepository.save(
                new Transaction("TRANSFER_OUT",
                        amount, LocalDateTime.now(),from));

        transactionRepository.save(
                new Transaction("TRANSFER_IN",
                        amount, LocalDateTime.now(), to));
    }


    public List<Account> getAllAccounts() {
        return  accountRepository.findAll();
    }

    public Account updateAccount(Long id,Account updatedAccount){
        Account existingAccount = getAccount(id);
        existingAccount.setAccountHolderName(updatedAccount.getAccountHolderName());
        existingAccount.setAccountNumber(updatedAccount.getAccountNumber());
        existingAccount.setBalance(updatedAccount.getBalance());

        return accountRepository.save(existingAccount);
    }

    public void deleteAccount(Long id){
        Account account = getAccount(id);
        accountRepository.delete(account);
    }
}
