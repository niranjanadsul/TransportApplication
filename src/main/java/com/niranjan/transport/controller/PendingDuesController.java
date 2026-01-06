package com.niranjan.transport.controller;

import com.niranjan.transport.dto.StudentPendingFeeDTO;
import com.niranjan.transport.dto.TourTripPendingDTO;
import com.niranjan.transport.service.PendingDuesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports/pending")
public class PendingDuesController {

    private final PendingDuesService pendingDuesService;

    public PendingDuesController(PendingDuesService pendingDuesService) {
        this.pendingDuesService = pendingDuesService;
    }

    @GetMapping("/students")
    public List<StudentPendingFeeDTO> studentPending() {
        return pendingDuesService.getStudentPendingFees();
    }

    @GetMapping("/tours")
    public List<TourTripPendingDTO> tourPending() {
        return pendingDuesService.getTourTripPendingPayments();
    }
}
