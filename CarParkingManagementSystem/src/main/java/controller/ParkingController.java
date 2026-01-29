package com.example.carparking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.carparking.service.ParkingService;

@Controller
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("parkingList", parkingService.getAllParking());
        return "index";
    }
}
