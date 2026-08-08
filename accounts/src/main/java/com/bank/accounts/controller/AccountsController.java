package com.bank.accounts.controller;

import com.bank.accounts.constants.AccountsConstants;
import com.bank.accounts.dto.CustomerDTO;
import com.bank.accounts.dto.ErrorResponseDTO;
import com.bank.accounts.dto.ResponseDTO;
import com.bank.accounts.model.Customer;
import com.bank.accounts.service.AccountsServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {
    @Autowired
    private AccountsServiceImpl accountsService;;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@Validated @RequestBody CustomerDTO customerDTO) {
        accountsService.createAccount(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@Valid
                                                               @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                               @RequestParam String mobileNumber){

        CustomerDTO customerDTO = accountsService.fetchAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.FOUND).body(customerDTO);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccount(@Valid @RequestBody CustomerDTO customerDTO){

        boolean isUpdated = accountsService.updateAccount(customerDTO);

        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                         String mobileNumber){

        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }

    }
}
