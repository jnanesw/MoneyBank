package com.bank.accounts.service;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.AccountsDTO;
import com.bank.accounts.dto.CustomerDTO;
import com.bank.accounts.exception.CustomerAlreadyExistsException;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.AccountsMapper;
import com.bank.accounts.mapper.CustomerMapper;
import com.bank.accounts.model.Accounts;
import com.bank.accounts.model.Customer;
import com.bank.accounts.repository.AccountsRepository;
import com.bank.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements AccountsService{

    @Autowired
    private AccountsRepository accountsRepo;
    @Autowired
    private CustomerRepository customerRepo;


//    private AccountsMapper accountsMapper;

//    private CustomerMapper customerMapper;

    @Override
    public void createAccount(CustomerDTO customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepo.findByMobileNumber(customer.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer Already registered with the give mobileNumber\" + customer.getMobileNumber()");
        }

//        customer.setCreatedAt(LocalDate.now());
//        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepo.save(customer);

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        Accounts accounts = new Accounts();
        accounts.setCustomerId(savedCustomer.getCustomerId());
        accounts.setAccountNumber(randomAccNumber);
//        accounts.setCreatedAt(LocalDate.now());
//        accounts.setCreatedBy("Anonymous");
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);

        Accounts savedAccount = accountsRepo.save(accounts);
    }

    public CustomerDTO fetchAccount(String mobileNumber){
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
//        IO.println("Customer: " + customer);
        Accounts accounts = accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
//        IO.println("Accounts: " + accounts);
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDto(customer, new CustomerDTO());
        customerDTO.setAccountsDTO(AccountsMapper.mapToAccountsDto(accounts, new AccountsDTO()));

        return customerDTO;
    }

    public boolean updateAccount(CustomerDTO customerDTO){
        AccountsDTO accountsDTO = customerDTO.getAccountsDTO();
        boolean isUpdated = false;
        if(accountsDTO != null){
            Accounts accounts = accountsRepo.findByAccountNumber(accountsDTO.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account", "AccountNumber", accountsDTO.getAccountNumber().toString())
            );

            Accounts savedAccounts = accountsRepo.save(AccountsMapper.mapToAccounts(accountsDTO, accounts));

            Long customerId = savedAccounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    ()->new ResourceNotFoundException("Customer", "MobileNumber", customerDTO.getMobileNumber())
            );

            customerRepo.save(CustomerMapper.mapToCustomer(customerDTO, customer));

            isUpdated = true;
        }
        return isUpdated;
    }

    public boolean deleteAccount(String mobileNumber){
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber)
        );

        accountsRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.delete(customer);

        return true;
    }
}
