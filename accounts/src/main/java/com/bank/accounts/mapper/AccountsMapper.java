package com.bank.accounts.mapper;

import com.bank.accounts.dto.AccountsDTO;
import com.bank.accounts.model.Accounts;

public class AccountsMapper {
    public static AccountsDTO mapToAccountsDto(Accounts accounts, AccountsDTO accountsDto){
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDTO accountsDto, Accounts accounts){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
