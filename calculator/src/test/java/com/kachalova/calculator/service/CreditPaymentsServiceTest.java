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
import static com.kachalova.calculator.enums.Position.MIDDLE_MANAGER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CreditPaymentsServiceTest {
    @Autowired
    private CreditPaymentsService creditPaymentsService;

    @Test
    void calculateMonthlyPayment() {
        BigDecimal amount = creditPaymentsService.calculateMonthlyPayment(BigDecimal.valueOf(15),
                BigDecimal.valueOf(300000), 18);
        assertEquals(BigDecimal.valueOf(18715.4400000), amount);
    }

    @Test
    void calculatePsk() {
        BigDecimal psk = creditPaymentsService.calculatePsk(BigDecimal.valueOf(18715.44), 18);
        assertEquals(BigDecimal.valueOf(336877.92), psk);
    }

    @Test
    void getPaymentSchedule() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(MIDDLE_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(18)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .gender(FEMALE)
                .birthdate(LocalDate.now().minusYears(30))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.now())
                .maritalStatus(MARRIED)
                .employment(employmentDto)
                .accountNumber("12312")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        List<PaymentScheduleElementDto> actual = creditPaymentsService.getPaymentSchedule(scoringDataDTO,
                BigDecimal.valueOf(18715.44),
                BigDecimal.valueOf(15),
                (LocalDate.of(2024, 6, 02)));

        List<PaymentScheduleElementDto> expect = List.of(
                PaymentScheduleElementDto.builder().number(1).date(LocalDate.of(2024, 7, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(15026.92)).debtPayment(BigDecimal.valueOf(3688.52)).remainingDebt(BigDecimal.valueOf(284973.08)).build(),
                PaymentScheduleElementDto.builder().number(2).date(LocalDate.of(2024, 8, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(15094.88)).debtPayment(BigDecimal.valueOf(3620.56)).remainingDebt(BigDecimal.valueOf(269878.20)).build(),
                PaymentScheduleElementDto.builder().number(3).date(LocalDate.of(2024, 9, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(15286.66)).debtPayment(BigDecimal.valueOf(3428.78)).remainingDebt(BigDecimal.valueOf(254591.54)).build(),
                PaymentScheduleElementDto.builder().number(4).date(LocalDate.of(2024, 10, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(15585.22)).debtPayment(BigDecimal.valueOf(3130.22)).remainingDebt(BigDecimal.valueOf(239006.32)).build(),
                PaymentScheduleElementDto.builder().number(5).date(LocalDate.of(2024, 11, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(15678.88)).debtPayment(BigDecimal.valueOf(3036.56)).remainingDebt(BigDecimal.valueOf(223327.44)).build(),
                PaymentScheduleElementDto.builder().number(6).date(LocalDate.of(2024, 12, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(15969.61)).debtPayment(BigDecimal.valueOf(2745.83)).remainingDebt(BigDecimal.valueOf(207357.83)).build(),
                PaymentScheduleElementDto.builder().number(7).date(LocalDate.of(2025, 1, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(16073.76)).debtPayment(BigDecimal.valueOf(2641.68)).remainingDebt(BigDecimal.valueOf(191284.07)).build(),
                PaymentScheduleElementDto.builder().number(8).date(LocalDate.of(2025, 2, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(16278.53)).debtPayment(BigDecimal.valueOf(2436.91)).remainingDebt(BigDecimal.valueOf(175005.54)).build(),
                PaymentScheduleElementDto.builder().number(9).date(LocalDate.of(2025, 3, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(16701.68)).debtPayment(BigDecimal.valueOf(2013.76)).remainingDebt(BigDecimal.valueOf(158303.86)).build(),
                PaymentScheduleElementDto.builder().number(10).date(LocalDate.of(2025, 4, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(16698.69)).debtPayment(BigDecimal.valueOf(2016.75)).remainingDebt(BigDecimal.valueOf(141605.17)).build(),
                PaymentScheduleElementDto.builder().number(11).date(LocalDate.of(2025, 5, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(16969.62)).debtPayment(BigDecimal.valueOf(1745.82)).remainingDebt(BigDecimal.valueOf(124635.55)).build(),
                PaymentScheduleElementDto.builder().number(12).date(LocalDate.of(2025, 6, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(17127.62)).debtPayment(BigDecimal.valueOf(1587.82)).remainingDebt(BigDecimal.valueOf(107507.93)).build(),
                PaymentScheduleElementDto.builder().number(13).date(LocalDate.of(2025, 7, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(17390.00)).debtPayment(BigDecimal.valueOf(1325.44)).remainingDebt(BigDecimal.valueOf(90117.93)).build(),
                PaymentScheduleElementDto.builder().number(14).date(LocalDate.of(2025, 8, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(17567.36)).debtPayment(BigDecimal.valueOf(1148.08)).remainingDebt(BigDecimal.valueOf(72550.57)).build(),
                PaymentScheduleElementDto.builder().number(15).date(LocalDate.of(2025, 9, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(17791.17)).debtPayment(BigDecimal.valueOf(924.27)).remainingDebt(BigDecimal.valueOf(54759.40)).build(),
                PaymentScheduleElementDto.builder().number(16).date(LocalDate.of(2025, 10, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(18040.32)).debtPayment(BigDecimal.valueOf(675.12)).remainingDebt(BigDecimal.valueOf(36719.08)).build(),
                PaymentScheduleElementDto.builder().number(17).date(LocalDate.of(2025, 11, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(18247.65)).debtPayment(BigDecimal.valueOf(467.79)).remainingDebt(BigDecimal.valueOf(18471.43)).build(),
                PaymentScheduleElementDto.builder().number(18).date(LocalDate.of(2025, 12, 2)).totalPayment(BigDecimal.valueOf(18715.44)).interestPayment(BigDecimal.valueOf(18487.71)).debtPayment(BigDecimal.valueOf(227.73)).remainingDebt(BigDecimal.valueOf(0)).build()
        );

        for (int i = 0; i < actual.size(); i++) {
            PaymentScheduleElementDto a = actual.get(i);
            PaymentScheduleElementDto e = expect.get(i);
            assertEquals(a.getNumber(), e.getNumber());
            assertEquals(a.getDate(), e.getDate());
            assertEquals(0, a.getTotalPayment().compareTo(e.getTotalPayment()));
            assertEquals(0, a.getInterestPayment().compareTo(e.getInterestPayment()));
            assertEquals(0, a.getDebtPayment().compareTo(e.getDebtPayment()));
            assertEquals(0, a.getRemainingDebt().compareTo(e.getRemainingDebt()));

        }
    }

}