package com.app.url.shortener.service;

import com.app.url.shortener.model.User;
import com.app.url.shortener.model.UserDTO;
import com.app.url.shortener.repository.UserRepository;
import lombok.AllArgsConstructor;
import com.app.url.shortener.config.SpringConfig.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserDTO saveNewUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return toDTO(savedUser);
    }

    private UserDTO toDTO(User savedUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setUsername(savedUser.getUsername());
        return userDTO;
    }
}
