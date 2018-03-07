package com.konfx.api.stock;

import com.konfx.api.stock.model.Stock;
import com.konfx.api.stock.model.dto.OperationDTO;
import com.konfx.api.stock.model.dto.StockDTO;
import com.konfx.api.stock.repository.OperationRepository;
import com.konfx.api.stock.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {

	private StockRepository stockRepository;

	@PostMapping
	public StockDTO addStock(@RequestBody StockDTO stockDto) {
		return stockRepository.save(Stock.from(stockDto)).toDTO();
	}

	@GetMapping
	public List<StockDTO> getStocks() {
		return Stock.toDTO(stockRepository.findAll());
	}

	@PutMapping("/{id}")
	public void editStock(@PathVariable Integer id, @RequestBody Stock stock) {
		Stock existingStock = stockRepository.findOne(id);
		Assert.notNull(existingStock, "Stock not found");
//		stock.setId(id);
		stockRepository.save(stock);
	}

	@DeleteMapping("/{id}")
	public void deleteStock(@PathVariable Integer id) {
		stockRepository.delete(id);
	}
}
