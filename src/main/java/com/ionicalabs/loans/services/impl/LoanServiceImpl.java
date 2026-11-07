package com.ionicalabs.loans.services.impl;

import com.ionicalabs.loans.controllers.dto.LoansDto;
import com.ionicalabs.loans.entities.Loans;
import com.ionicalabs.loans.expetions.ResourceNotFoundException;
import com.ionicalabs.loans.repositories.LoanRepository;
import com.ionicalabs.loans.services.LoanService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.ionicalabs.loans.constants.LoanConstants.HOME_LOAN;
import static com.ionicalabs.loans.constants.LoanConstants.NEW_LOAN_LIMIT;
import static com.ionicalabs.loans.mappers.LoanMapper.mapToLoans;
import static com.ionicalabs.loans.mappers.LoanMapper.mapToLoansDTO;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loans createLoan(LoansDto loansDto) {
        return loanRepository.save(createNewLoan(loansDto));
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Optional<Loans> loans = loanRepository.findByMobileNumber(mobileNumber);
        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber);
        }
        return mapToLoansDTO(loans.get());
    }

    @Override
    public LoansDto updateLoan(LoansDto loansDto) {
        String mobileNumber = loansDto.mobileNumber();
        Loans loan = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                );
        Loans loansToSave = mapToLoans(loansDto);
        loansToSave.setId(loan.getId());
        loanRepository.save(loansToSave);
        return loansDto;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
       Loans loan = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                );
        loanRepository.deleteById(loan.getId());
        return true;
    }

    private Loans createNewLoan(LoansDto loansDto) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(loansDto.mobileNumber());
        newLoan.setLoanType(loansDto.loanType());
        newLoan.setTotalLoan(loansDto.totalLoan());
        newLoan.setAmountPaid(loansDto.amountPaid());
        newLoan.setOutstandingAmount(loansDto.outstandingAmount());
        return newLoan;
    }
}
