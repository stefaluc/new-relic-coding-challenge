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

    @GetMapping(value = "/customers", params = {"!search", "!sortBy", "!sortDir"})
    List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping(value = "/customers", params = "search")
    List<Customer> searchCustomers(@RequestParam("search") String search) {
        return customerService.searchCustomers(search);
    }

    @GetMapping(value = "/customers", params = {"sortBy", "sortDir"})
    List<Customer> sortCustomers(
            @RequestParam("sortBy") String sortBy,
            @RequestParam("sortDir") String sortDir
    ) {
        return customerService.sortCustomers(sortBy, sortDir);
    }

    @PostMapping("/customers/seedDb")
    void seedDb(@RequestParam("numCustomers") Integer numCustomers) {
        customerService.seedDb(numCustomers);
    }
}
