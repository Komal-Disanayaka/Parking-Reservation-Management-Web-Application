package com.parking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_lots")
public class ParkingLot {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Lot name is required")
    @Column(name = "lot_name", nullable = false)
    private String lotName;
    
    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Column(nullable = false)
    private Integer capacity;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LotStatus status = LotStatus.AVAILABLE;
    
    @Column(name = "occupied_slots")
    private Integer occupiedSlots = 0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum LotStatus {
        AVAILABLE, MAINTENANCE, CLOSED
    }
    
    // Constructors
    public ParkingLot() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public ParkingLot(String lotName, String location, String description, Integer capacity) {
        this();
        this.lotName = lotName;
        this.location = location;
        this.description = description;
        this.capacity = capacity;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLotName() {
        return lotName;
    }
    
    public void setLotName(String lotName) {
        this.lotName = lotName;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LotStatus getStatus() {
        return status;
    }
    
    public void setStatus(LotStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
    
    public Integer getOccupiedSlots() {
        return occupiedSlots;
    }
    
    public void setOccupiedSlots(Integer occupiedSlots) {
        this.occupiedSlots = occupiedSlots;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Computed properties
    public Integer getAvailableSlots() {
        return capacity - occupiedSlots;
    }
    
    public Double getOccupancyPercentage() {
        if (capacity == 0) return 0.0;
        return (occupiedSlots.doubleValue() / capacity.doubleValue()) * 100;
    }
    
    public boolean isActive() {
        return status == LotStatus.AVAILABLE;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}