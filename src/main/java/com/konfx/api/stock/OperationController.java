package com.konfx.api.stock;

import com.konfx.api.stock.model.Operation;
import com.konfx.api.stock.model.dto.OperationDTO;
import com.konfx.api.stock.repository.OperationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.konfx.api.stock.model.Operation.toDTO;

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
	public List<OperationDTO> getOperations() {
		return toDTO(operationRepository.findAll());
	}

	@DeleteMapping("/{id}")
	public void deleteOperation(@PathVariable Integer id) {
		operationRepository.delete(id);
	}
}
