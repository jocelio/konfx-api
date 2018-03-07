package com.konfx.api.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.konfx.api.stock.model.Operation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationDTO {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("stock_id")
	private Integer stockId;

	@JsonProperty("operation_type_id")
	private Integer operationTypeId;

	@JsonProperty("date")
	private LocalDateTime dateTime;

	public static OperationDTO from(Operation operation){
		return OperationDTO.builder()
					.id(operation.getId())
					.stockId(operation.getStock().getId())
					.operationTypeId(operation.getOperationType().ordinal())
					.dateTime(operation.getDateTime()).build();

	}

}
