package com.konfx.api.stock;

import com.konfx.api.stock.model.Operation;
import com.konfx.api.stock.repository.OperationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {

	private OperationRepository operationRepository;

	public OperationController(OperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}

	@PostMapping
	public Operation addOperation(@RequestBody Operation operation) {
		return operationRepository.save(operation);
	}

	@GetMapping
	public List<Operation> getOperations() {
		return operationRepository.findAll();
	}
}
