package com.konfx.api.employee.rest;

import com.konfx.api.employee.model.Employee;
import com.konfx.api.employee.repository.EmployeeRepository;
import io.vavr.control.Try;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
@NoArgsConstructor
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@PostMapping
	public ResponseEntity add(@RequestBody Employee employee) {
		Employee saved = employeeRepository.save(employee);
		URI uri = Try.of(() -> new URI(saved.getId().toString())).get();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	@GetMapping("/{id}")
	public Employee getOne(@PathVariable Integer id) {
		return employeeRepository.findOne(id);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		employeeRepository.delete(id);
	}

	private URI createURI (Employee employee){
		try {
			return new URI("api/employee"+employee.getId());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;

	}
}
