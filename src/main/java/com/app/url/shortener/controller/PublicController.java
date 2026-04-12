package com.app.url.shortener.controller;

import com.app.url.shortener.model.UserDTO;
import com.app.url.shortener.service.UserService;
import com.app.url.shortener.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
@Slf4j
@RestControllerAdvice
public class PublicController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDTO userDTO) {
        try {
            userService.saveNewUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        try {
//            authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                           userDTO.getUsername(), userDTO.getPassword()
                    )
            );
//          generate the token
//            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String jwtToken = jwtUtil.generateToken(userDTO.getUsername());
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Authentication failed: {}", e);
            return new ResponseEntity<>("Wrong credentials", HttpStatus.BAD_REQUEST);
        }
    }
}
