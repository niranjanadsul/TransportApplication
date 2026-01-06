package com.niranjan.transport.controller;

import com.niranjan.transport.dto.TourTripAccountingDTO;
import com.niranjan.transport.service.TourTripAccountingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports/tours")
public class TourTripAccountingController {

    private final TourTripAccountingService service;

    public TourTripAccountingController(
            TourTripAccountingService service) {
        this.service = service;
    }

    @GetMapping("/accounting")
    public List<TourTripAccountingDTO> getTourAccounting() {
        return service.getTripAccountingReport();
    }
}
