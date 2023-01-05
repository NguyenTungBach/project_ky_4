package com.example.project_sem_4.controller;

import com.example.project_sem_4.database.dto.booking.BookingDTO;
import com.example.project_sem_4.database.dto.order.OrderDTO;
import com.example.project_sem_4.database.dto.order.OrderDetailDTO;
import com.example.project_sem_4.database.search_body.OrderSearchBody;
import com.example.project_sem_4.service.order.OrderDetailService;
import com.example.project_sem_4.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @PostMapping("/create")
    public ResponseEntity createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.create(orderDTO), HttpStatus.OK);
    }
    @PostMapping("/createOrderDetail")
    public ResponseEntity createOrderDetail(@RequestBody @Valid OrderDetailDTO orderDetailDTO) {
        return new ResponseEntity<>(orderDetailService.create(orderDetailDTO), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity createOrderDetail(@RequestBody OrderSearchBody orderSearchBody) {
        return new ResponseEntity<>(orderService.findAll(orderSearchBody), HttpStatus.OK);
    }
}
