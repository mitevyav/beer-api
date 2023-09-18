package com.springguru.lombok.controller;

import com.springguru.lombok.model.CustomerDTO;
import com.springguru.lombok.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.springguru.lombok.controller.CustomerController.CUSTOMER_PATH;

@RequiredArgsConstructor
@RequestMapping(CUSTOMER_PATH)
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{id}";

    @Autowired
    private final CustomerService customerService;


    @PatchMapping("{id}")
    public ResponseEntity patchCustomerById(@PathVariable("id") UUID id, @RequestBody CustomerDTO customer) {
        customerService.patchById(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity updateCustomer(@PathVariable("id") UUID id, @RequestBody CustomerDTO customer) {
        customerService.updateCustomer(id, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO craeatedCustomer = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + craeatedCustomer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

    @GetMapping(value = "{id}")
    public CustomerDTO getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id).orElseThrow(NotFoundException::new);
    }
}
