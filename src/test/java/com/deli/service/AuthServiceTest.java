package com.deli.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.deli.models.user.User;
import com.deli.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthService authService;

    @Test
    void testLoadUserByUsername_whenExists_returnUser() {
        String email = "test@example.com";
        User mockUser = new User("Teste", email, "passwordtest");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Ação
        UserDetails result = authService.loadUserByUsername(email);

        // Verificação
        assertEquals(mockUser.getEmail(), result.getUsername());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void testLoadUserByUsername_whenNotExists_throwsUserNotFoundException() {
        // Configuração
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Ação e Verificação
        assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername(email);
        });

        verify(userRepository).findByEmail(email);
    }
}
