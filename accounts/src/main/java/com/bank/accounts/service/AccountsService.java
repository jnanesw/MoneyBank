package com.bank.accounts.service;

import com.bank.accounts.dto.CustomerDTO;

public interface AccountsService {
    void createAccount(CustomerDTO customerDto);
    CustomerDTO fetchAccount(String mobileNumber);
    public boolean updateAccount(CustomerDTO customerDTO);

}
