package com.example.demo.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.data.model.Customer;
import com.example.demo.data.repository.CustomerRepository;
import com.github.javafaker.Faker;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CustomerRepository repository) {
        return args -> {
            Faker faker = new Faker();
            for (int i = 0; i < 100; i++) {
                Customer c = new Customer();
                c.setFirstName(faker.name().firstName());
                c.setLastName(faker.name().lastName());
                c.setCompanyName(faker.company().name());
                log.info("Preloading " + repository.save(c));
            }
        };
    }
}
