package com.microservice.paymentservice.service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.paymentservice.entity.Payment;
import com.microservice.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public Payment savePayments(Payment p) {
		p.setPaymentStatus(paymentProcess());
		p.setTransactionId(UUID.randomUUID().toString());
		return paymentRepository.save(p);
	}
	public String paymentProcess() {
		return new Random().nextBoolean()?"success":"false";
	}
	public List<Payment> findByOrderId(int id) {
		// TODO Auto-generated method stub
		return paymentRepository.findByOderId(id);
	}
	
	

}
