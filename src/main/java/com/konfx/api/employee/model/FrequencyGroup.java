package com.konfx.api.employee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Wither
public class FrequencyGroup {

	private Employee employee;

	private Payroll payroll;

	private LocalDate date;

	private List<LocalDateTime> sortedFrequencyPoint;

	private BigDecimal workedHours;

	private BigDecimal extraHours;


}
