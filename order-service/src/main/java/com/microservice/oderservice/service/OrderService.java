package com.microservice.oderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.oderservice.common.Payment;
import com.microservice.oderservice.common.TransactionRequest;
import com.microservice.oderservice.common.TransactionResponse;
import com.microservice.oderservice.entity.Order;
import com.microservice.oderservice.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	
	public TransactionResponse saveOrder(TransactionRequest request) {
		String msg;
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOderId(order.getId());
		payment.setAmount(order.getPrice());
		Payment paymentRspnse = restTemplate.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment,
				Payment.class);
		msg = paymentRspnse.getPaymentStatus().equals("success") ? saveOrderIfPaymentSuccess(order)
				: "Payment order added to cart";

		return new TransactionResponse(order, paymentRspnse.getTransactionId(), paymentRspnse.getAmount(), msg);
	}

	private String saveOrderIfPaymentSuccess(Order order) {
		orderRepository.save(order);
		return "Payment Done order booked successfully";
	}

	public TransactionResponse getOrderDetilasById(int id1) {
		Order order = orderRepository.findById(id1).get();
		int id =20;
		Payment[] p = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/get/{id}",Payment[].class,id);
	//	restTemplate.getForObject(null, null)
	//	Payment p = paymentRspnse.getBody();
		return  new TransactionResponse(order, p[0].getTransactionId(), p[0].getAmount(), "Details Got");
	}
}
