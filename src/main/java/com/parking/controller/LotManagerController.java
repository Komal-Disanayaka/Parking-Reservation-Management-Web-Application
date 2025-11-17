package com.parking.controller;

import com.parking.model.ParkingLot;
import com.parking.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/lot-manager")
@PreAuthorize("hasRole('LOT_MANAGER')")
public class LotManagerController {
    
    @Autowired
    private ParkingLotService parkingLotService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<ParkingLot> parkingLots = parkingLotService.getAllParkingLots();
        model.addAttribute("parkingLots", parkingLots);
        model.addAttribute("totalLots", parkingLotService.getTotalLots());
        model.addAttribute("availableLots", parkingLotService.getAvailableLotsCount());
        model.addAttribute("maintenanceLots", parkingLotService.getMaintenanceLotsCount());
        model.addAttribute("closedLots", parkingLotService.getClosedLotsCount());
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", auth.getName());
        
        return "lot-manager/dashboard";
    }
    
    @GetMapping("/lots")
    public String allLots(Model model) {
        List<ParkingLot> parkingLots = parkingLotService.getAllParkingLots();
        model.addAttribute("parkingLots", parkingLots);
        return "lot-manager/all-lots";
    }
    
    @GetMapping("/create-lot")
    public String createLotForm(Model model) {
        model.addAttribute("parkingLot", new ParkingLot());
        return "lot-manager/create-lot";
    }
    
    @PostMapping("/create-lot")
    public String createLot(@Valid @ModelAttribute("parkingLot") ParkingLot parkingLot, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "lot-manager/create-lot";
        }
        
        if (parkingLotService.existsByLotName(parkingLot.getLotName())) {
            result.rejectValue("lotName", "error.parkingLot", "A parking lot with this name already exists");
            return "lot-manager/create-lot";
        }
        
        try {
            parkingLotService.saveParkingLot(parkingLot);
            redirectAttributes.addFlashAttribute("successMessage", "Parking lot created successfully!");
            return "redirect:/lot-manager/lots";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating parking lot: " + e.getMessage());
            return "redirect:/lot-manager/create-lot";
        }
    }
    
    @GetMapping("/edit-lot/{id}")
    public String editLotForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ParkingLot> parkingLot = parkingLotService.getParkingLotById(id);
        
        if (parkingLot.isPresent()) {
            model.addAttribute("parkingLot", parkingLot.get());
            return "lot-manager/edit-lot";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Parking lot not found");
            return "redirect:/lot-manager/lots";
        }
    }
    
    @PostMapping("/edit-lot/{id}")
    public String editLot(@PathVariable Long id, 
                         @Valid @ModelAttribute("parkingLot") ParkingLot parkingLot, 
                         BindingResult result, 
                         RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "lot-manager/edit-lot";
        }
        
        // Check if another lot with the same name exists (excluding current lot)
        Optional<ParkingLot> existingLot = parkingLotService.getParkingLotByName(parkingLot.getLotName());
        if (existingLot.isPresent() && !existingLot.get().getId().equals(id)) {
            result.rejectValue("lotName", "error.parkingLot", "A parking lot with this name already exists");
            return "lot-manager/edit-lot";
        }
        
        try {
            ParkingLot updatedLot = parkingLotService.updateParkingLot(
                id, 
                parkingLot.getLotName(), 
                parkingLot.getLocation(), 
                parkingLot.getDescription(), 
                parkingLot.getCapacity(),
                parkingLot.getStatus()
            );
            
            if (updatedLot != null) {
                redirectAttributes.addFlashAttribute("successMessage", "Parking lot updated successfully!");
                return "redirect:/lot-manager/lots";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Parking lot not found");
                return "redirect:/lot-manager/lots";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating parking lot: " + e.getMessage());
            return "redirect:/lot-manager/edit-lot/" + id;
        }
    }
    
    @GetMapping("/delete-lot/{id}")
    public String deleteLotConfirm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ParkingLot> parkingLot = parkingLotService.getParkingLotById(id);
        
        if (parkingLot.isPresent()) {
            model.addAttribute("parkingLot", parkingLot.get());
            return "lot-manager/delete-confirm";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Parking lot not found");
            return "redirect:/lot-manager/lots";
        }
    }
    
    @PostMapping("/delete-lot/{id}")
    public String deleteLot(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = parkingLotService.deleteParkingLot(id);
            
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "Parking lot deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Parking lot not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting parking lot: " + e.getMessage());
        }
        
        return "redirect:/lot-manager/lots";
    }
    
    @GetMapping("/lot-details/{id}")
    public String lotDetails(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<ParkingLot> parkingLot = parkingLotService.getParkingLotById(id);
        
        if (parkingLot.isPresent()) {
            model.addAttribute("parkingLot", parkingLot.get());
            return "lot-manager/lot-details";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Parking lot not found");
            return "redirect:/lot-manager/lots";
        }
    }
}