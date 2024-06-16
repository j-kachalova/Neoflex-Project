package com.kachalova.deal.dto;

import com.kachalova.deal.enums.ApplicationStatus;
import com.kachalova.deal.enums.ChangeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatementStatusHistoryDto {
    private ApplicationStatus status;
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;
}
