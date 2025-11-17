package com.parking.service;

import com.parking.model.User;
import com.parking.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void initializeDefaultUsers() {
        // Create default lot manager if not exists
        if (!userRepository.findByUsername("lotm").isPresent()) {
            User lotManager = new User();
            lotManager.setUsername("lotm");
            lotManager.setPassword(passwordEncoder.encode("lotm123"));
            lotManager.setEmail("lotmanager@parking.com");
            lotManager.setFirstName("Lot");
            lotManager.setLastName("Manager");
            lotManager.setRole(User.Role.LOT_MANAGER);
            
            userRepository.save(lotManager);
            System.out.println("Default lot manager user created: lotm/lotm123");
        }
    }
}