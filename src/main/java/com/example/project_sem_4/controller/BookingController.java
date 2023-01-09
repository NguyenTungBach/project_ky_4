package com.example.project_sem_4.controller;

import com.example.project_sem_4.database.dto.ServiceDTO;
import com.example.project_sem_4.database.dto.booking.BookingDTO;
import com.example.project_sem_4.database.search_body.BlogSearchBody;
import com.example.project_sem_4.database.search_body.BookingSearchBody;
import com.example.project_sem_4.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity createBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        return new ResponseEntity<>(bookingService.createBooking(bookingDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity detailBooking(@PathVariable String id) {
        return new ResponseEntity<>(bookingService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity updateBooking(@PathVariable String id,@RequestBody @Valid BookingDTO bookingDTO) {
        return new ResponseEntity<>(bookingService.updateBooking(bookingDTO,id), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee_idAndDate_booking")
    public ResponseEntity findAllByEmployee_idAndDate_booking(@RequestParam Integer employee_id,@RequestParam String date_booking) {
        return new ResponseEntity<>(bookingService.findAllByEmployee_idAndDate_booking(employee_id,date_booking), HttpStatus.OK);
    }
    @PostMapping("/search")
    public ResponseEntity searchBooking(@RequestBody BookingSearchBody bookingSearchBody) {
        return new ResponseEntity(bookingService.findAll(bookingSearchBody), HttpStatus.OK);
    }
}
