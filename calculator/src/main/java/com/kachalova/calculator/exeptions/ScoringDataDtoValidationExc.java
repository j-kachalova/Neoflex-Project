package com.kachalova.calculator.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ScoringDataDtoValidationExc extends Exception {
    public ScoringDataDtoValidationExc(String message) {
        super(message);
    }
}
