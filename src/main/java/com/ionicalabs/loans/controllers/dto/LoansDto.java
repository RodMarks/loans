package com.ionicalabs.loans.controllers.dto;

public record LoansDto(
        String mobileNumber,
        String loanNumber,
        String loanType,
        int totalLoan,
        int amountPaid,
        int outstandingAmount
) {
}
