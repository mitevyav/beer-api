package com.springguru.lombok.mappers;

import com.springguru.lombok.entities.Customer;
import com.springguru.lombok.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDTO(Customer customer);
}
