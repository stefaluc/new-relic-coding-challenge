package com.newrelic.challenge.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.newrelic.challenge.Application;
import com.newrelic.challenge.data.model.Customer;
import com.newrelic.challenge.data.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository repository;

    @After
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void testGetCustomers() throws Exception {
        createTestCustomer("John", "Doe", "Test");
        createTestCustomer("Rob", "Lowe", "Test2");
        mvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].firstName", is("Rob")));
    }

    @Test
    public void testSearchCustomers() throws Exception {
        createTestCustomer("John", "Doe", "Test");
        createTestCustomer("Rob", "Lowe", "Test2");
        createTestCustomer("Chris", "Doe", "Test2");
        mvc.perform(get("/customers?search=Doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].lastName", is("Doe")));
        mvc.perform(get("/customers?search=ob lo")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].lastName", is("Lowe")));
    }

    @Test
    public void testSortCustomers() throws Exception {
        createTestCustomer("John", "Doe", "Test");
        createTestCustomer("Rob", "Lowe", "Test2");
        mvc.perform(get("/customers?sortBy=lastName&sortDir=desc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].firstName", is("Rob")));
    }

    @Test
    public void testSeedDb() throws Exception {
        int numCustomers = 20;
        mvc.perform(post("/customers/seedDb?numCustomers=" + numCustomers));
        List<Customer> seeded = repository.findAll();
        assertThat(seeded.size()).isEqualTo(numCustomers);
    }

    private void createTestCustomer(String firstName, String lastName, String companyName) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setFullName(firstName + " " + lastName);
        customer.setCompanyName(companyName);
        repository.saveAndFlush(customer);
    }
}
