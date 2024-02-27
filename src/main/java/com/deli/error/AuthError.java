package com.deli.error;

public class AuthError extends RuntimeException {
    
    public AuthError(String message){
        super(message);
    }
}
