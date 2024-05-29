package com.kachalova.calculator.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanStatementRequestDto {
    @NotNull
    @DecimalMin(value = "30000", message = "Loan amount must be at least 30000")
    private BigDecimal amount;

    @NotNull
    @Min(value = 6, message = "Loan term must be at least 6 months")
    private Integer term;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{2,30}", message = "First name must consist of 2 to 30 Latin letters")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{2,30}", message = "Last name must consist of 2 to 30 Latin letters")
    private String lastName;

    @Pattern(regexp = "[a-zA-Z]{2,30}", message = "Middle name must consist of 2 to 30 Latin letters")
    private String middleName;

    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull
    @Past
    private LocalDate birthdate;

    @NotBlank
    @Pattern(regexp = "\\d{4}", message = "Passport series must be 4 digits")
    private String passportSeries;

    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "Passport number must be 6 digits")
    private String passportNumber;

}
