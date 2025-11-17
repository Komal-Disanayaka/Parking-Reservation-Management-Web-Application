package com.parking.config;

import com.parking.model.ParkingLot;
import com.parking.model.User;
import com.parking.repository.ParkingLotRepository;
import com.parking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        
        // Create default lot manager user if not exists
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
        
        // Create default admin user if not exists
        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@parking.com");
            admin.setFirstName("System");
            admin.setLastName("Administrator");
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Default admin user created: admin/admin123");
        }
        
        // Create sample parking lots if none exist
        if (parkingLotRepository.count() == 0) {
            ParkingLot lot1 = new ParkingLot();
            lot1.setLotName("Garden Area - Ground Floor");
            lot1.setLocation("Garden Area");
            lot1.setDescription("Open parking space with green surroundings");
            lot1.setCapacity(250);
            lot1.setOccupiedSlots(0);
            lot1.setStatus(ParkingLot.LotStatus.AVAILABLE);
            parkingLotRepository.save(lot1);
            
            ParkingLot lot2 = new ParkingLot();
            lot2.setLotName("Main Building - Ground Floor");
            lot2.setLocation("Main Building");
            lot2.setDescription("Multi-level covered parking");
            lot2.setCapacity(200);
            lot2.setOccupiedSlots(20);
            lot2.setStatus(ParkingLot.LotStatus.AVAILABLE);
            parkingLotRepository.save(lot2);
            
            System.out.println("Sample parking lots created");
        }
    }
}