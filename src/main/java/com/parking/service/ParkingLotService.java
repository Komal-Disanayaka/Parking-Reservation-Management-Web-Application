package com.parking.service;

import com.parking.model.ParkingLot;
import com.parking.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParkingLotService {
    
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    
    public List<ParkingLot> getAllParkingLots() {
        return parkingLotRepository.findAllOrderByCreatedAtDesc();
    }
    
    public List<ParkingLot> getAvailableParkingLots() {
        return parkingLotRepository.findAllAvailableLots();
    }
    
    public Optional<ParkingLot> getParkingLotById(Long id) {
        return parkingLotRepository.findById(id);
    }
    
    public Optional<ParkingLot> getParkingLotByName(String lotName) {
        return parkingLotRepository.findByLotName(lotName);
    }
    
    public ParkingLot saveParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }
    
    public ParkingLot createParkingLot(String lotName, String location, String description, Integer capacity) {
        ParkingLot parkingLot = new ParkingLot(lotName, location, description, capacity);
        return parkingLotRepository.save(parkingLot);
    }
    
    public ParkingLot updateParkingLot(Long id, String lotName, String location, String description, Integer capacity, ParkingLot.LotStatus status) {
        Optional<ParkingLot> optionalLot = parkingLotRepository.findById(id);
        if (optionalLot.isPresent()) {
            ParkingLot parkingLot = optionalLot.get();
            parkingLot.setLotName(lotName);
            parkingLot.setLocation(location);
            parkingLot.setDescription(description);
            parkingLot.setCapacity(capacity);
            parkingLot.setStatus(status);
            return parkingLotRepository.save(parkingLot);
        }
        return null;
    }
    
    public boolean deleteParkingLot(Long id) {
        if (parkingLotRepository.existsById(id)) {
            parkingLotRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean existsByLotName(String lotName) {
        return parkingLotRepository.findByLotName(lotName).isPresent();
    }
    
    public List<ParkingLot> getParkingLotsByLocation(String location) {
        return parkingLotRepository.findByLocation(location);
    }
    
    public List<ParkingLot> getParkingLotsByStatus(ParkingLot.LotStatus status) {
        return parkingLotRepository.findByStatus(status);
    }
    
    public Long getTotalLots() {
        return parkingLotRepository.count();
    }
    
    public Long getAvailableLotsCount() {
        return parkingLotRepository.countByStatus(ParkingLot.LotStatus.AVAILABLE);
    }
    
    public Long getMaintenanceLotsCount() {
        return parkingLotRepository.countByStatus(ParkingLot.LotStatus.MAINTENANCE);
    }
    
    public Long getClosedLotsCount() {
        return parkingLotRepository.countByStatus(ParkingLot.LotStatus.CLOSED);
    }
    
    public ParkingLot updateOccupancy(Long lotId, Integer occupiedSlots) {
        Optional<ParkingLot> optionalLot = parkingLotRepository.findById(lotId);
        if (optionalLot.isPresent()) {
            ParkingLot parkingLot = optionalLot.get();
            parkingLot.setOccupiedSlots(occupiedSlots);
            return parkingLotRepository.save(parkingLot);
        }
        return null;
    }
}