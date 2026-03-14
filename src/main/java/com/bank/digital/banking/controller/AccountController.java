package com.bank.digital.banking.controller;

import com.bank.digital.banking.entity.Account;
import com.bank.digital.banking.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController { 

    private final AccountService  accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account){
        return  accountService.createAccount(account);
    }



    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id){
        return  accountService.getAccount(id);

    }
    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PostMapping("{id}/deposit")
    public Account deposit(@PathVariable Long id,@RequestParam double amount){
        return  accountService.deposit(id,amount);
    }

    @PostMapping("{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestParam double amount){
        return accountService.withdraw(id, amount);
    }




    @PostMapping("/transfer")

    public String transfer(@RequestParam Long fromId,@RequestParam Long toId,
                           @RequestParam double amount){
        accountService.transfer(fromId,toId,amount);
        return "Transfer successful";
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id,
                                 @RequestBody Account account){
        return accountService.updateAccount(id,account);
    }
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return "Account deleted successfully";
    }

}
