package com.kachalova.deal.service;

import com.kachalova.deal.dto.FinishRegistrationRequestDto;

public interface FinishRegistration {
    void finishRegistration(FinishRegistrationRequestDto finishRegistrationRequestDto,String statementId);
}
