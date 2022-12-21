package com.example.project_sem_4.controller;

import com.example.project_sem_4.database.dto.ServiceDTO;
import com.example.project_sem_4.database.dto.booking.BookingDTO;
import com.example.project_sem_4.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity createBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        return new ResponseEntity<>(bookingService.create(bookingDTO), HttpStatus.OK);
    }
}
