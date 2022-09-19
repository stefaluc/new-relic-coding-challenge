package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.example.demo.data.model.Customer;
import com.example.demo.data.repository.CustomerRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private CustomerRepository repository;

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
		List<Customer> allCustomers = this.repository.findAll();
		System.out.println("Number of customers: " + allCustomers.size());

		Customer newCustomer = new Customer();
		newCustomer.setFirstName("John");
		newCustomer.setLastName("Doe");
		newCustomer.setCompanyName("Test Company");
		System.out.println("Saving new customer...");
		this.repository.save(newCustomer);

		allCustomers = this.repository.findAll();
		System.out.println("Number of customers: " + allCustomers.size());
	}
}
