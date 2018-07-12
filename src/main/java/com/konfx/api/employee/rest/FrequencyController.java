package com.konfx.api.employee.rest;

import com.konfx.api.employee.model.Frequency;
import com.konfx.api.employee.repository.FrequencyRepository;
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
	private FrequencyRepository frequencyRepository;

	@PostMapping
	public ResponseEntity add(@RequestBody Frequency frequency) {
		Frequency saved = frequencyRepository.save(frequency);
		URI uri = Try.of(() -> new URI(saved.getId().toString())).get();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public List<Frequency> getAll() {
		return frequencyRepository.findAll();
	}

	@GetMapping("/{id}")
	public Frequency getOne(@PathVariable Integer id) {
		return frequencyRepository.findOne(id);
	}

}
