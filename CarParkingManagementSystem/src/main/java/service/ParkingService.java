package com.example.carparking.service;

import java.util.List;
import com.example.carparking.model.Parking;

public interface ParkingService {

    Parking saveParking(Parking parking);
    List<Parking> getAllParking();
}
