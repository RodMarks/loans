package com.ionicalabs.loans.mappers;

import com.ionicalabs.loans.controllers.dto.LoansDto;
import com.ionicalabs.loans.entities.Loans;

public class LoanMapper {

    public static Loans mapToLoans(LoansDto dto) {
        Loans loans = new Loans();
        loans.setMobileNumber(dto.mobileNumber());
        loans.setLoanNumber(dto.loanNumber());
        loans.setLoanType(dto.loanType());
        loans.setTotalLoan(dto.totalLoan());
        loans.setAmountPaid(dto.amountPaid());
        loans.setOutstandingAmount(dto.outstandingAmount());
        return loans;
    }

    public static LoansDto mapToLoansDTO(Loans loans) {
        return new LoansDto(
                loans.getMobileNumber(),
                loans.getLoanNumber(),
                loans.getLoanType(),
                loans.getTotalLoan(),
                loans.getAmountPaid(),
                loans.getOutstandingAmount());
    }
}
