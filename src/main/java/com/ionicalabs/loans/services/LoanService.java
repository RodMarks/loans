package com.ionicalabs.loans.services;

import com.ionicalabs.loans.controllers.dto.LoansDto;
import com.ionicalabs.loans.entities.Loans;

public interface LoanService {
    Loans createLoan(LoansDto loansDto);
    LoansDto fetchLoan(String mobileNumber);
    LoansDto updateLoan(LoansDto loansDto);
    boolean deleteLoan(String mobileNumber);
}
