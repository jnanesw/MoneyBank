package com.bank.accounts.repository;

import com.bank.accounts.model.Accounts;
import com.bank.accounts.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Optional<Accounts> findByCustomerId(Long customerId);
    Optional<Accounts> findByAccountNumber(Long accountNumber);

    @Transactional
    void deleteByCustomerId(Long customerId);
}
