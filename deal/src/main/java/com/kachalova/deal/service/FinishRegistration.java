package com.kachalova.deal.service;

import com.kachalova.deal.dto.FinishRegistrationRequestDto;

import java.util.UUID;

public interface FinishRegistration {
    void finishRegistration(FinishRegistrationRequestDto finishRegistrationRequestDto, UUID statementId);
}
