package com.springguru.lombok.controller;

import com.springguru.lombok.entities.Customer;
import com.springguru.lombok.mappers.CustomerMapper;
import com.springguru.lombok.model.CustomerDTO;
import com.springguru.lombok.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;


    @Test
    void testPatchCustomerNotFound() {
        assertThrows(
                NotFoundException.class,
                () -> customerController.patchCustomerById(UUID.randomUUID(),
                        CustomerDTO.builder().build()));
    }

    @Test
    @Transactional
    @Rollback
    void testPatchCustomerFound() {
        Customer customerEntity = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerEntity);
        val updatedName = "UPDATED";
        customerDTO.setName(updatedName);

        customerController.patchCustomerById(customerDTO.getId(), customerDTO);

        val updated = customerRepository.findById(customerDTO.getId()).get();
        assertThat(updated.getName()).isEqualTo(updatedName);
    }

    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomer(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void deleteByIdFound() {
        val customer = customerRepository.findAll().get(0);

        customerController.deleteCustomer(customer.getId());

        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(
                NotFoundException.class,
                () -> customerController.updateCustomer(
                        UUID.randomUUID(), CustomerDTO.builder().build()
                ));
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateExistingCustomerFound() {
        Customer customerEntity = customerRepository.findAll().get(0);
        CustomerDTO customer = customerMapper.customerToCustomerDTO(customerEntity);
        val newName = "BILBO BAGGINS";
        customer.setName(newName);

        customerController.updateCustomer(customer.getId(), customer);

        val updatedCustomer = customerRepository.getReferenceById(customer.getId());
        assertThat(updatedCustomer.getName()).isEqualTo(newName);
    }

    @Test
    @Transactional
    @Rollback
    void testAddNewCustomer() {
        CustomerDTO newCustomer = CustomerDTO.builder()
                .name("Bilbo Baggins")
                .build();

        ResponseEntity response = customerController.createCustomer(newCustomer);
        String[] path = response.getHeaders().getLocation().getPath().toString().split("/");
        UUID id = UUID.fromString(path[4]);

        assertThat(customerRepository.findById(id)).isNotNull();
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> customerList = customerController.listCustomers();

        assertThat(customerList).hasSize(3);
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyList() {
        customerRepository.deleteAll();

        assertThat(customerRepository.findAll()).isEmpty();
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO dto = customerController.getCustomerById(customer.getId());

        assertThat(dto).isNotNull();
    }


}