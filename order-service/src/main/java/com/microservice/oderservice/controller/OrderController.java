package com.microservice.oderservice.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.oderservice.common.Payment;
import com.microservice.oderservice.common.TransactionRequest;
import com.microservice.oderservice.common.TransactionResponse;
import com.microservice.oderservice.entity.Order;
import com.microservice.oderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private static final String order_service = "order_service";
   @Autowired
   private OrderService orderService;
   
   int attempts = 1;
   
   @PostMapping("/bookOrder")
  // @CircuitBreaker(name = service_name,fallbackMethod ="getDefualtPaymentDetails" )
   public TransactionResponse saveOrder(@RequestBody TransactionRequest request) {
	   return orderService.saveOrder(request);
	   
   }
   
   @GetMapping("/orderHistory/{id}")
 //  @CircuitBreaker(name = order_service,fallbackMethod = "getDefualtPaymentDetails" )
   @Retry(name =order_service, fallbackMethod = "getDefualtPaymentDetails" )
   public TransactionResponse getOrderDetials(@PathVariable("id") int id) {
	   System.out.println("Retry method called "+attempts+" time "+ new Date());
       return orderService.getOrderDetilasById(id);
   }
   
   
   
   public TransactionResponse getDefualtPaymentDetails(int id,Exception ex) {
	   Order r = new Order(98, "TextBook", 23, 100.9);
	   TransactionResponse t = new TransactionResponse(r, "dummy",r.getPrice(), "Dummy");
	   System.out.println("ORDER SERVICE : FOllbck method colled");
	return t;
	   
   }
	
}
