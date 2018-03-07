package com.konfx.api.stock.model;

import com.konfx.api.stock.model.dto.OperationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}
