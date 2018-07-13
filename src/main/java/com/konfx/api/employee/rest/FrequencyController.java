package com.konfx.api.employee.rest;

import com.konfx.api.employee.model.Frequency;
import com.konfx.api.employee.model.FrequencyGroup;
import com.konfx.api.employee.repository.FrequencyRepository;
import com.konfx.api.employee.service.FrequencyService;
import com.konfx.api.employee.service.FrequencyServiceImp;
import io.vavr.control.Try;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/frequency")
@NoArgsConstructor
public class FrequencyController {

	@Autowired
	private FrequencyService frequencyService;

	@PostMapping
	public ResponseEntity add(@RequestBody Frequency frequency) {
		Frequency saved = frequencyService.save(frequency);
		return ResponseEntity.ok(saved);
	}

	@GetMapping
	public List<Frequency> getAll() {
		return frequencyService.findAll();
	}

	@GetMapping("/grouped")
	public List<FrequencyGroup> getAllGroup() {
		return frequencyService.findGroup();
	}

	@GetMapping("/{id}")
	public Frequency getOne(@PathVariable Integer id) {
		return frequencyService.findOne(id);
	}

}
