package com.czavala.productmanagementsystem.mapper;

import com.czavala.productmanagementsystem.dto.customer.CustomerDto;
import com.czavala.productmanagementsystem.persistance.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public CustomerDto mapToCustomerDto(User user) {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstname(user.getFirstname());
        customerDto.setLastname(user.getLastname());
        customerDto.setUsername(user.getUsername());
        customerDto.setEmail(user.getEmail());
        customerDto.setRole(user.getRole().name());
        customerDto.setCreatedAt(user.getCreatedAt());
        customerDto.setLastModified(user.getLastModified());

        return customerDto;
    }
}
