package com.konfx.api.stock.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.konfx.api.stock.model.dto.OperationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Stock stock;
	@Enumerated(EnumType.ORDINAL)
	private OperationType operationType;

	private LocalDateTime dateTime;

	public static Operation from(OperationDTO operationDTO){
		return Operation.builder()
						.stock(Stock.builder().id(operationDTO.getStockId()).build())
						.operationType(OperationType.RETIRADA)
						.dateTime(LocalDateTime.now())
						.build();
	}

	public OperationDTO toDTO(){
		return OperationDTO.from(this);
	}

	public static List<OperationDTO> toDTO(List<Operation> operations){
		return operations.stream().map(s -> s.toDTO()).collect(toList());
	}
}
