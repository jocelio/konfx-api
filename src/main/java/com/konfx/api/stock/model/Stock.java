package com.konfx.api.stock.model;

import com.konfx.api.stock.model.dto.StockDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	private Product product;
	@OneToOne
	private Vendor vendor;
	private Integer version;
	private BigDecimal unitPrice;
	private Integer lazyQty;
	@OneToMany
	private List<Operation> operations;

	public static Stock from(StockDTO stockDTO){
		return Stock.builder()
				.product(Product.builder().id(stockDTO.getProductId()).build())
				.vendor(Vendor.builder().id(stockDTO.getVendorId()).build())
				.version(stockDTO.getVersion())
				.unitPrice(stockDTO.getUnitPrice())
				.lazyQty(stockDTO.getLazyQty()).build();

	}

	public StockDTO toDTO(){
		return StockDTO.from(this);
	}

	public static List<StockDTO> toDTO(List<Stock> stocks){
		return stocks.stream().map(s -> s.toDTO()).collect(toList());
	}

}
