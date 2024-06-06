package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.ScoringDataDto;

import javax.validation.Valid;
import java.math.BigDecimal;

public interface ScoringService {
    BigDecimal makeScoring(@Valid ScoringDataDto scoringDataDto, BigDecimal rate);
}
