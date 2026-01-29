package com.example.carparking.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.carparking.model.Parking;
import com.example.carparking.repository.ParkingRepository;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    @Override
    public Parking saveParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    @Override
    public List<Parking> getAllParking() {
        return parkingRepository.findAll();
    }
}
