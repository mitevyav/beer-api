package com.springguru.lombok.service;

import com.springguru.lombok.entities.Customer;
import com.springguru.lombok.mappers.CustomerMapper;
import com.springguru.lombok.model.CustomerDTO;
import com.springguru.lombok.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        val customerEntity = customerMapper.customerDTOToCustomer(customer);
        val result = customerRepository.save(customerEntity);
        return customerMapper.customerToCustomerDTO(result);
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(UUID id, CustomerDTO customer) {

        Optional<Customer> existingOptional = customerRepository.findById(id);

        if (existingOptional.isEmpty()) {
            return Optional.empty();
        }
        Customer existing = existingOptional.get();


        existing.setName(customer.getName());
        val updated = customerRepository.save(existing);

        return Optional.of(customerMapper.customerToCustomerDTO(updated));
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (!customerRepository.existsById(id)) {
            return false;
        }

        customerRepository.deleteById(id);

        return true;
    }

    @Override
    public Optional<CustomerDTO> patchById(UUID id, CustomerDTO customer) {
        Optional<Customer> existingOptional = customerRepository.findById(id);

        if (existingOptional.isEmpty()) {
            return Optional.empty();
        }

        Customer existing = existingOptional.get();

        if (StringUtils.hasText(customer.getName())) {
            existing.setName(customer.getName());
        }

        val updated = customerRepository.save(existing);
        return Optional.of(customerMapper.customerToCustomerDTO(updated));
    }
}
