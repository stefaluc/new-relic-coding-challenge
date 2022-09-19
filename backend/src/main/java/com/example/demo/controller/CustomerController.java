package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.model.Customer;
import com.example.demo.service.CustomerService;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping("/customers/seedDb")
    void seedDb(@RequestParam("numCustomers") Integer numCustomers) {
        customerService.seedDb(numCustomers);
    }
}
