package com.parking.controller;

import com.parking.model.ParkingLot;
import com.parking.model.User;
import com.parking.service.ParkingLotService;
import com.parking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ParkingLotService parkingLotService;
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, 
                              BindingResult result, 
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        // Check for validation errors
        if (result.hasErrors()) {
            return "register";
        }
        
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Registration successful! Please login with your credentials.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }
    
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }
        
        if (logout != null) {
            model.addAttribute("successMessage", "You have been logged out successfully!");
        }
        
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        
        // Add available parking lots for users to see
        List<ParkingLot> availableParkingLots = parkingLotService.getAvailableParkingLots();
        model.addAttribute("parkingLots", availableParkingLots);
        model.addAttribute("totalAvailableLots", availableParkingLots.size());
        
        return "dashboard";
    }
}