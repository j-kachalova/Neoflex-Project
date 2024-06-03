package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.EmploymentDto;
import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.kachalova.calculator.enums.EmploymentStatus.EMPLOYED;
import static com.kachalova.calculator.enums.Gender.FEMALE;
import static com.kachalova.calculator.enums.MaritalStatus.MARRIED;
import static com.kachalova.calculator.enums.Position.TOP_MANAGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
//@RunWith(SpringRunner.class)
class CreditPaymentsServiceTest {
    @Autowired
    CreditPaymentsService creditPaymentsService;

    @Test
    void calculateMonthlyPayment() {
        BigDecimal amount = creditPaymentsService.calculateMonthlyPayment(BigDecimal.valueOf(15), BigDecimal.valueOf(300000), 18);
        assertEquals(BigDecimal.valueOf(18715.4400000), amount);
    }

    @Test
    void calculatePsk() {
        BigDecimal psk = creditPaymentsService.calculatePsk(BigDecimal.valueOf(18715.44), 18);
        assertEquals(BigDecimal.valueOf(336877.92), psk);
    }

    @Test
    void getPaymentSchedule() {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000), 18, "test", "test",
                "test", FEMALE, LocalDate.now().minusYears(30), "1234", "123456", LocalDate.now(), "test", MARRIED,
                0, new EmploymentDto(EMPLOYED, "123", BigDecimal.valueOf(100000), TOP_MANAGER, 20, 20), "12312", true, true);
        List<PaymentScheduleElementDto> actual = creditPaymentsService.getPaymentSchedule(scoringDataDTO, BigDecimal.valueOf(18715.44), BigDecimal.valueOf(15), LocalDate.of(2024, 6, 02));

        List<PaymentScheduleElementDto> expect = List.of(
                new PaymentScheduleElementDto(1, LocalDate.of(2024, 7, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(15026.92), BigDecimal.valueOf(3688.52), BigDecimal.valueOf(284973.08)),
                new PaymentScheduleElementDto(2, LocalDate.of(2024, 8, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(15094.88), BigDecimal.valueOf(3620.56), BigDecimal.valueOf(269878.20)),
                new PaymentScheduleElementDto(3, LocalDate.of(2024, 9, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(15286.66), BigDecimal.valueOf(3428.78), BigDecimal.valueOf(254591.54)),
                new PaymentScheduleElementDto(4, LocalDate.of(2024, 10, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(15585.22), BigDecimal.valueOf(3130.22), BigDecimal.valueOf(239006.32)),
                new PaymentScheduleElementDto(5, LocalDate.of(2024, 11, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(15678.88), BigDecimal.valueOf(3036.56), BigDecimal.valueOf(223327.44)),
                new PaymentScheduleElementDto(6, LocalDate.of(2024, 12, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(15969.61), BigDecimal.valueOf(2745.83), BigDecimal.valueOf(207357.83)),
                new PaymentScheduleElementDto(7, LocalDate.of(2025, 1, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(16073.76), BigDecimal.valueOf(2641.68), BigDecimal.valueOf(191284.07)),
                new PaymentScheduleElementDto(8, LocalDate.of(2025, 2, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(16278.53), BigDecimal.valueOf(2436.91), BigDecimal.valueOf(175005.54)),
                new PaymentScheduleElementDto(9, LocalDate.of(2025, 3, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(16701.68), BigDecimal.valueOf(2013.76), BigDecimal.valueOf(158303.86)),
                new PaymentScheduleElementDto(10, LocalDate.of(2025, 4, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(16698.69), BigDecimal.valueOf(2016.75), BigDecimal.valueOf(141605.17)),
                new PaymentScheduleElementDto(11, LocalDate.of(2025, 5, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(16969.62), BigDecimal.valueOf(1745.82), BigDecimal.valueOf(124635.55)),
                new PaymentScheduleElementDto(12, LocalDate.of(2025, 6, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(17127.62), BigDecimal.valueOf(1587.82), BigDecimal.valueOf(107507.93)),
                new PaymentScheduleElementDto(13, LocalDate.of(2025, 7, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(17390.00), BigDecimal.valueOf(1325.44), BigDecimal.valueOf(90117.93)),
                new PaymentScheduleElementDto(14, LocalDate.of(2025, 8, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(17567.36), BigDecimal.valueOf(1148.08), BigDecimal.valueOf(72550.57)),
                new PaymentScheduleElementDto(15, LocalDate.of(2025, 9, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(17791.17), BigDecimal.valueOf(924.27), BigDecimal.valueOf(54759.40)),
                new PaymentScheduleElementDto(16, LocalDate.of(2025, 10, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(18040.32), BigDecimal.valueOf(675.12), BigDecimal.valueOf(36719.08)),
                new PaymentScheduleElementDto(17, LocalDate.of(2025, 11, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(18247.65), BigDecimal.valueOf(467.79), BigDecimal.valueOf(18471.43)),
                new PaymentScheduleElementDto(18, LocalDate.of(2025, 12, 2), BigDecimal.valueOf(18715.44), BigDecimal.valueOf(18487.71), BigDecimal.valueOf(227.73), BigDecimal.valueOf(0))
        );

        for (int i = 0; i < actual.size(); i++) {
            PaymentScheduleElementDto a = actual.get(i);
            PaymentScheduleElementDto e = expect.get(i);
            assertEquals(a.getNumber(), e.getNumber());
            assertEquals(a.getDate(), e.getDate());
            assertTrue(a.getTotalPayment().compareTo(e.getTotalPayment()) == 0);
            assertTrue(a.getInterestPayment().compareTo(e.getInterestPayment()) == 0);
            assertTrue(a.getDebtPayment().compareTo(e.getDebtPayment()) == 0);
            assertTrue(a.getRemainingDebt().compareTo(e.getRemainingDebt()) == 0);

        }
    }

}