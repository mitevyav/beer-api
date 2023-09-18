package com.springguru.lombok.service;

import com.springguru.lombok.mappers.CustomerMapper;
import com.springguru.lombok.model.CustomerDTO;
import com.springguru.lombok.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDTO(
                customerRepository.findById(id)
                        .orElse(null)));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomer(UUID id, CustomerDTO customer) {

    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void patchById(UUID id, CustomerDTO customer) {

    }
}
