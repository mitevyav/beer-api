package com.springguru.lombok.service;

import com.springguru.lombok.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();

    Customer getCustomerById(UUID id);

    Customer saveCustomer(Customer customer);

    void updateCustomer(UUID id, Customer customer);
}
