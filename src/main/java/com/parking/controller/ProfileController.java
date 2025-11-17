package com.parking.controller;

import com.parking.model.User;
import com.parking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String viewProfile(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser);
        return "profile/view";
    }
    
    @GetMapping("/edit")
    public String editProfile(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser);
        return "profile/edit";
    }
    
    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        if (result.hasFieldErrors("email") || result.hasFieldErrors("firstName") || 
            result.hasFieldErrors("lastName")) {
            User currentUser = userService.getCurrentUser();
            user.setUsername(currentUser.getUsername()); // Preserve username for display
            return "profile/edit";
        }
        
        try {
            userService.updateUserProfile(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Profile updated successfully!");
            return "redirect:/profile";
        } catch (RuntimeException e) {
            User currentUser = userService.getCurrentUser();
            user.setUsername(currentUser.getUsername()); // Preserve username for display
            model.addAttribute("errorMessage", e.getMessage());
            return "profile/edit";
        }
    }
    
    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "profile/change-password";
    }
    
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        
        // Validate passwords
        if (newPassword.length() < 6) {
            model.addAttribute("errorMessage", "New password must be at least 6 characters long!");
            return "profile/change-password";
        }
        
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "New passwords do not match!");
            return "profile/change-password";
        }
        
        if (userService.changePassword(oldPassword, newPassword)) {
            redirectAttributes.addFlashAttribute("successMessage", 
                "Password changed successfully!");
            return "redirect:/profile";
        } else {
            model.addAttribute("errorMessage", "Current password is incorrect!");
            return "profile/change-password";
        }
    }
    
    @GetMapping("/delete")
    public String deleteProfileConfirm() {
        return "profile/delete-confirm";
    }
    
    @PostMapping("/delete")
    public String deleteProfile(RedirectAttributes redirectAttributes) {
        try {
            userService.deleteCurrentUser();
            // Invalidate the session
            SecurityContextHolder.clearContext();
            redirectAttributes.addFlashAttribute("successMessage", 
                "Your account has been deleted successfully!");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error deleting account. Please try again.");
            return "redirect:/profile";
        }
    }
}