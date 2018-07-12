package com.konfx.api.employee.rest;

import com.konfx.api.employee.model.Payroll;
import com.konfx.api.employee.repository.PayrollRepository;
import io.vavr.control.Try;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/payroll")
@NoArgsConstructor
public class PayrollController {

	@Autowired
	private PayrollRepository payrollRepository;

	@PostMapping
	public ResponseEntity add(@RequestBody Payroll payroll) {
		Payroll saved = payrollRepository.save(payroll);
		URI uri = Try.of(() -> new URI(saved.getId().toString())).get();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public List<Payroll> getAll() {
		return payrollRepository.findAll();
	}

	@GetMapping("/{id}")
	public Payroll getOne(@PathVariable Integer id) {
		return payrollRepository.findOne(id);
	}

}
