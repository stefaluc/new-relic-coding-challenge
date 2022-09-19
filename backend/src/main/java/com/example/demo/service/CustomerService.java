package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.data.model.Customer;
import com.example.demo.data.repository.CustomerRepository;
import com.github.javafaker.Faker;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> searchCustomers(String search) {
        return customerRepository.searchCustomers(search);
    }

    public List<Customer> sortCustomers(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return customerRepository.findAll(sort);
    }

    public void seedDb(Integer numCustomers) {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            Customer c = new Customer();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String fullName = firstName + " " + lastName;
            c.setFirstName(firstName);
            c.setLastName(lastName);
            c.setFullName(fullName);
            c.setCompanyName(faker.company().name());
            log.info("Seeding " + customerRepository.save(c));
        }
    }
}
