package com.czavala.productmanagementsystem.services.impl;

import com.czavala.productmanagementsystem.dto.auth.SaveRegisterUserDto;
import com.czavala.productmanagementsystem.exceptions.InvalidPasswordException;
import com.czavala.productmanagementsystem.persistance.Utils.Role;
import com.czavala.productmanagementsystem.persistance.entities.User;
import com.czavala.productmanagementsystem.persistance.repository.UserRepository;
import com.czavala.productmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User registerOneCustomer(SaveRegisterUserDto saveRegisterUserDto) {

        validatePassword(saveRegisterUserDto);

        User newUser = new User();
        newUser.setFirstname(saveRegisterUserDto.getFirstname());
        newUser.setLastname(saveRegisterUserDto.getLastname());
        newUser.setEmail(saveRegisterUserDto.getEmail());
        newUser.setUsername(saveRegisterUserDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(saveRegisterUserDto.getPassword())); // se encripta la clave del user
        newUser.setRole(Role.ROLE_CUSTOMER); // por defecto se le asigan el rol customer
        newUser.setCreatedAt(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    // si password y repeatedPasswords no coinciden o no contienen texto, arroja exception acorde
    private void validatePassword(SaveRegisterUserDto saveRegisterUserDto) {

        if (!StringUtils.hasText(saveRegisterUserDto.getPassword()) || !StringUtils.hasText(saveRegisterUserDto.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords do not match");
        }

        if (!saveRegisterUserDto.getPassword().equals(saveRegisterUserDto.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords do not match");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
