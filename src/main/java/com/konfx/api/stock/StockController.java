package com.konfx.api.stock;

import com.konfx.api.stock.model.Operation;
import com.konfx.api.stock.model.Stock;
import com.konfx.api.stock.model.dto.StockDTO;
import com.konfx.api.stock.repository.OperationRepository;
import com.konfx.api.stock.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.konfx.api.stock.model.OperationType.ENTRADA;
import static com.konfx.api.stock.model.Stock.toDTO;
import static java.util.stream.IntStream.range;

@RestController
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {

	private StockRepository stockRepository;

	private OperationRepository operationRepository;

	@PostMapping
	public StockDTO addStock(@RequestBody StockDTO stockDto) {

		Stock stock = stockRepository.save(Stock.from(stockDto));

		range(0, stockDto.getLazyQty())
				.mapToObj(i ->
						Operation.builder().dateTime(LocalDateTime.now())
						.operationType(ENTRADA)
						.stock(stock)
						.build()
				).forEach(operationRepository::save);

		return stockRepository.findOne(stock.getId()).toDTO();
	}

	@GetMapping
	public List<StockDTO> getStocks() {
		return toDTO(stockRepository.findAll());
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
