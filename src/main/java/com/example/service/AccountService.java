package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account registerUser(Account account){

        return accountRepository.save(account);
    }

    public Boolean accountExist(Account account){

        if(accountRepository.findByUsername(account.getUsername()) != null)
            return true;
        
        return false;
    }

    public Account loginUser(Account account){

        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

    public Account verifyCridentials(Account account){

        Account foundAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if( foundAccount == null){
            return null;
        }

        return foundAccount;
    }

    public Boolean isPoster(int accountId){

        Account account = accountRepository.findByAccountId(accountId); 
        if(account != null){
            return true;
        }
            
        return false;
    }
}
