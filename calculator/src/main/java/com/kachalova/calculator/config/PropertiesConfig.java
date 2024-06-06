package com.kachalova.calculator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class PropertiesConfig {
    private BigDecimal keyRate;
    private BigDecimal selfEmployedRate;
    private BigDecimal businessOwnerRate;
    private BigDecimal middleManagerRate;
    private BigDecimal topManagerRate;
    private BigDecimal marriedRate;
    private BigDecimal divorcedRate;
    private BigDecimal womanAgeRate;
    private BigDecimal manAgeRate;
    private BigDecimal nonBinaryRate;
    private Integer numberSalaries;
    private BigDecimal insuranceRate;
    private BigDecimal salaryRate;
    private Integer minTotalWorkExperience;
    private Integer minCurrentWorkExperience;
}