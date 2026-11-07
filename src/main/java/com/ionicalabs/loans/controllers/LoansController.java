package com.ionicalabs.loans.controllers;

import com.ionicalabs.loans.constants.LoanConstants;
import com.ionicalabs.loans.controllers.dto.LoansDto;
import com.ionicalabs.loans.controllers.dto.ResponseDto;
import com.ionicalabs.loans.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ionicalabs.loans.constants.LoanConstants.MESSAGE_200;
import static com.ionicalabs.loans.constants.LoanConstants.STATUS_200;
import static java.util.Objects.nonNull;

@RestController
public class LoansController implements LoansApi {

    private LoanService loanService;

    public LoansController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public ResponseEntity<?> createLoan(LoansDto dto) {
        loanService.createLoan(dto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<LoansDto> fetchLoan(String mobileNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.fetchLoan(mobileNumber));
    }

    @Override
    public ResponseEntity<?> updateLoan(LoansDto dto) {
        LoansDto dtoUpdate = loanService.updateLoan(dto);
        if(nonNull(dtoUpdate)) {
            return ResponseEntity.status(HttpStatus.OK).body(dtoUpdate);
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
        }
    }

    @Override
    public ResponseEntity<?> deleteLoan(String mobileNumber) {
        boolean loanDeleted = loanService.deleteLoan(mobileNumber);
        if(loanDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_DELETE));
        }
    }
}
