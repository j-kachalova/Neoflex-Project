package com.kachalova.calculator.interfaces;

import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.exeptions.ScoringdataDtoValidationExc;

import javax.validation.Valid;
import java.math.BigDecimal;

public interface ScoringDataInt {
    BigDecimal makeScoring(@Valid ScoringDataDto scoringDataDto, BigDecimal rate) throws ScoringdataDtoValidationExc;
}
