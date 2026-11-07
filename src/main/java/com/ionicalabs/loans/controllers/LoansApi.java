package com.ionicalabs.loans.controllers;

import com.ionicalabs.loans.controllers.dto.LoansDto;
import com.ionicalabs.loans.controllers.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Loans", description = "CRUD REST APIs for managing loans in IonicaBank")
@RequestMapping(path = "/api/v1/loans", produces = MediaType.APPLICATION_JSON_VALUE)
public interface LoansApi {

    @Operation(summary = "Create loan", description = "Creates a new loan for the given mobile number")
    @ApiResponse(responseCode = "200", description = "Loan created")
    @PostMapping("/create")
    ResponseEntity<?> createLoan(@Valid @RequestBody LoansDto dto);

    @Operation(summary = "Fetch loan", description = "Fetches the loan details for the given mobile number")
    @ApiResponse(responseCode = "200", description = "Loan found",
            content = @Content(schema = @Schema(implementation = LoansDto.class)))
    @GetMapping("/fetch")
    ResponseEntity<LoansDto> fetchLoan(@RequestParam String mobileNumber);

    @Operation(summary = "Update loan", description = "Updates the loan details")
    @ApiResponse(responseCode = "200", description = "Loan updated",
            content = @Content(schema = @Schema(implementation = LoansDto.class)))
    @ApiResponse(responseCode = "417", description = "Update operation failed",
            content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    @PutMapping("/update")
    ResponseEntity<?> updateLoan(@Valid @RequestBody LoansDto dto);

    @Operation(summary = "Delete loan", description = "Deletes the loan for the given mobile number")
    @ApiResponse(responseCode = "200", description = "Loan deleted",
            content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    @ApiResponse(responseCode = "417", description = "Delete operation failed",
            content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    @DeleteMapping("/delete")
    ResponseEntity<?> deleteLoan(@RequestParam String mobileNumber);
}
