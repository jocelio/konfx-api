package com.konfx.api.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konfx.api.stock.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("product_id")
	private Integer productId;

	@JsonProperty("vendor_id")
	private Integer vendorId;

	@JsonProperty("version")
	private Integer version;

	@JsonProperty("unit_price")
	private BigDecimal unitPrice;

	@JsonProperty("lazy_qty")
	private Integer lazyQty;

	public static StockDTO from(Stock stock){
		return StockDTO.builder().id(stock.getId())
				.productId(stock.getProduct().getId())
				.vendorId(stock.getVendor().getId())
				.version(stock.getVersion())
				.unitPrice(stock.getUnitPrice())
				.lazyQty(stock.getLazyQty()).build();
	}

}
