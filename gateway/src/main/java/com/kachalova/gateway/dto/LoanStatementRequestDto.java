package com.kachalova.gateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanStatementRequestDto {
    @NotNull
    @DecimalMin(value = "30000", message = "Loan amount must be at least 30000")
    private BigDecimal amount;

    @NotNull
    @Min(value = 6, message = "Loan term must be at least 6 months")
    private Integer term;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{2,30}", message = "First name must consist of 2 to 30 Latin letters")
    @Schema(example = "Ivan")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{2,30}", message = "Last name must consist of 2 to 30 Latin letters")
    @Schema(example = "Ivanov")
    private String lastName;

    @Pattern(regexp = "[a-zA-Z]{2,30}", message = "Middle name must consist of 2 to 30 Latin letters")
    @Schema(example = "Ivanovich")
    private String middleName;

    @Email(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotNull
    private LocalDate birthdate;

    @NotBlank
    @Pattern(regexp = "\\d{4}", message = "Passport series must be 4 digits")
    private String passportSeries;

    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "Passport number must be 6 digits")
    private String passportNumber;

    @AssertTrue
    public boolean isValidBirthday() {

        return (LocalDate.now().getYear() - this.birthdate.getYear() > 18);
    }


}
