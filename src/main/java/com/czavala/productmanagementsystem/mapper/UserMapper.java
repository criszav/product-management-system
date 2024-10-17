package com.czavala.productmanagementsystem.mapper;

import com.czavala.productmanagementsystem.dto.customer.CustomerDto;
import com.czavala.productmanagementsystem.dto.customer.CustomerProfileDto;
import com.czavala.productmanagementsystem.persistance.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public CustomerDto mapToCustomerDto(User user) {

        if (user== null) return null;

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

    public CustomerProfileDto mapToCustomerProfileDto(User user) {

        if (user == null) return null;

        CustomerProfileDto customerProfileDto= new CustomerProfileDto();
        customerProfileDto.setId(user.getId());
        customerProfileDto.setFirstname(user.getFirstname());
        customerProfileDto.setLastname(user.getLastname());
        customerProfileDto.setUsername(user.getUsername());
        customerProfileDto.setEmail(user.getEmail());
        customerProfileDto.setRole(user.getRole().name());
        customerProfileDto.setCreatedAt(user.getCreatedAt());
        customerProfileDto.setLastModified(user.getLastModified());
        customerProfileDto.setAccountEnabled(user.isEnabled());
        customerProfileDto.setAuthorities(user.getAuthorities());

        return customerProfileDto;
    }
}
