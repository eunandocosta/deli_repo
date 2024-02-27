package com.deli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.deli.models.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
    UserDetails findByEmail(String email);

}