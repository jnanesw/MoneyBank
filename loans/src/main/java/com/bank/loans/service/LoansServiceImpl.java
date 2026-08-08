package com.bank.loans.service;

import com.bank.loans.constants.LoansConstants;
import com.bank.loans.dto.LoansDTO;
import com.bank.loans.exception.LoanAlreadyExistsException;
import com.bank.loans.exception.ResourceNotFoundException;
import com.bank.loans.mapper.LoansMapper;
import com.bank.loans.model.Loans;
import com.bank.loans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class LoansServiceImpl implements LoansService{

    @Autowired
    private LoanRepository loanRepo;

    private LoansMapper loansMapper;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loans = loanRepo.findByMobileNumber(mobileNumber);

        if(loans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber" + mobileNumber);
        }

        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        loanRepo.save(newLoan);
    }

    @Override
    public LoansDTO fetchLoanDetails(String mobileNumber) {
        Loans loans = loanRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        return LoansMapper.mapToLoansDTO(loans, new LoansDTO());
    }

    @Override
    public boolean updateLoan(LoansDTO loansDto) {
        Loans loans = loanRepo.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loanRepo.save(loans);
        return  true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loanRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loanRepo.deleteById(loans.getLoanId());
        return true;
    }
}
