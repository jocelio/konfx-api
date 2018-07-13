package com.konfx.api.employee.rest;

import com.konfx.api.employee.model.Payroll;
import com.konfx.api.employee.repository.PayrollRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/payroll")
@NoArgsConstructor
public class PayrollController {

	@Autowired
	private PayrollRepository payrollRepository;

	@PostMapping
	public ResponseEntity add(@RequestBody Payroll payroll) {
		Payroll saved = payrollRepository.save(payroll);
//		URI uri = Try.of(() -> new URI("http://localhost:8080/api/payroll/"+ saved.getId().toString())).get();
		return ok(saved);
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
