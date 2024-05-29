package com.czavala.productmanagementsystem.services;

import com.czavala.productmanagementsystem.dto.auth.SaveRegisterUserDto;
import com.czavala.productmanagementsystem.persistance.entities.User;

public interface UserService {

    User registerOneCustomer(SaveRegisterUserDto saveRegisterUserDto);
}
