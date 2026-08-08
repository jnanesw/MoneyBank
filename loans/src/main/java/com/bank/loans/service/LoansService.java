package com.bank.loans.service;

import com.bank.loans.dto.LoansDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;

@Service
public interface LoansService {
    void createLoan(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);

    LoansDTO fetchLoanDetails(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);

    boolean updateLoan(@Valid LoansDTO loansDto);

    boolean deleteLoan(String mobileNumber);
}
