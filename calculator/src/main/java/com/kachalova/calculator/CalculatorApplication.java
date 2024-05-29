package com.kachalova.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class CalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
		LocalDate birthday = LocalDate.of(1990, 1, 1);
		System.out.println(birthday.until(LocalDate.now()).getYears());
	}

}
