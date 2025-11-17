package com.parking.repository;

import com.parking.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    
    Optional<ParkingLot> findByLotName(String lotName);
    
    List<ParkingLot> findByLocation(String location);
    
    List<ParkingLot> findByStatus(ParkingLot.LotStatus status);
    
    @Query("SELECT p FROM ParkingLot p WHERE p.status = 'AVAILABLE' ORDER BY p.lotName")
    List<ParkingLot> findAllAvailableLots();
    
    @Query("SELECT p FROM ParkingLot p ORDER BY p.createdAt DESC")
    List<ParkingLot> findAllOrderByCreatedAtDesc();
    
    @Query("SELECT COUNT(p) FROM ParkingLot p WHERE p.status = :status")
    Long countByStatus(ParkingLot.LotStatus status);
}