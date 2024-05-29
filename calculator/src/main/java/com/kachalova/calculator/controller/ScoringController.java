package com.kachalova.calculator.controller;

import com.kachalova.calculator.dto.CreditDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoringController {
    @PostMapping("/calculator/calc")
    public CreditDto getCredit(@RequestBody ScoringDataDto scoringDataDto){
        CreditDto creditDto = new CreditDto();

        return creditDto;
    }
}
