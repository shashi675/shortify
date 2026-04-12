package com.app.url.shortener.controller;

import com.app.url.shortener.model.UserDTO;
import com.app.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
@Slf4j
@RestControllerAdvice
public class PublicController {
    private UserService userService;
//    private AuthenticationManager authenticationManager;

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

//    @PostMapping("/login")
//    public void login(@RequestBody UserDTO userDTO) {
//        try {
////            first authenticate the userDTO
////            authenticationManager.authenticate(
////                    new UsernamePasswordAuthenticationToken(
////                        userDTO.getUsername(),
////                        userDTO.getPassword()
////                    )
////            );
//
////            get the userDTO to generate the token
////            UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUserName());
////            String generatedToken = jwtUtil.generateToken(userDetails.getUsername());
//
//            SecurityContextHolder.getContext().setAuthentication(
//                    authenticationManager.authenticate(
//                            new UsernamePasswordAuthenticationToken(
//                                userDTO.getUsername(),
//                                userDTO.getPassword()
//                            )
//                    )
//            );
////            return new ResponseEntity<>(generatedToken, HttpStatus.OK);
//
//        } catch (Exception e) {
//            log.error("exception occurred while login", e);
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}
