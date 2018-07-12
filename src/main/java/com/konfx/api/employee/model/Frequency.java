package com.konfx.api.employee.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Wither
public class Frequency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Employee employee;

	@ManyToOne
	private Payroll payroll;

	private LocalDateTime dateTime;

	@JsonProperty("employee_id")
	@Transient
	public void setEmployeeId(Integer id) {
		employee = (employee == null) ? Employee.builder().id(id).build() : employee;
	}

	@JsonProperty("payroll_id")
	@Transient
	public void setPayrollId(Integer id) {
		payroll = (payroll == null) ? Payroll.builder().id(id).build() : payroll;
	}

}
