package com.deli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deli.models.customer.AuthenticationDTO;
import com.deli.models.customer.Customer;
import com.deli.models.customer.LoginResponseDTO;
import com.deli.models.customer.RegisterDTO;
import com.deli.repository.CustomerRepository;
import com.deli.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody @Valid AuthenticationDTO data){
        try {
            var customerPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            System.out.println(customerPassword); 
            var auth = this.authenticationManager.authenticate(customerPassword);

            var token = tokenService.generateToken((Customer) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token)); 
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha em autenticar");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid RegisterDTO data) {
        if(this.customerRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.ok().body("Email já está sendo utilizado");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Customer newCustomer = new Customer(data.name(), data.email(), encryptedPassword, data.phone());
        this.customerRepository.save(newCustomer);
        return ResponseEntity.ok().body("Registrado com sucesso");
    }
}
