package com.microservice.oderservice.common;

import com.microservice.oderservice.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

	private Order order;
	private String transactionId;
	private Double amount;
	private String message; 
}
