package com.bank.accounts.service;

import com.bank.accounts.dto.CustomerDetailsDTO;

public interface CustomerService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDTO fetchCustomerDetails(String mobileNumber);
}
