package com.springguru.lombok.controller;

import com.springguru.lombok.model.Customer;
import com.springguru.lombok.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @DeleteMapping("{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity updateCustomer(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        customerService.updateCustomer(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody Customer customer) {
        Customer craeatedCustomer = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + craeatedCustomer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id);
    }
}
