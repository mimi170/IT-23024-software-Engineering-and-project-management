package com.example.carparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.carparking.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {
}
