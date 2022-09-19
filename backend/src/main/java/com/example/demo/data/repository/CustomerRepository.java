package com.example.demo.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.data.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> { }
