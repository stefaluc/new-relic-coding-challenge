package com.example.demo.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.data.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT p FROM Customer p WHERE " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "OR LOWER(p.fullName) LIKE LOWER(CONCAT('%', :query, '%'))"
    )
    List<Customer> searchCustomers(String query);
}
