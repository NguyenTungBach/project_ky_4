package com.example.project_sem_4.controller;

import com.example.project_sem_4.database.dto.AccountDTO;
import com.example.project_sem_4.database.dto.RegisterDTO;
import com.example.project_sem_4.database.dto.VoucherDTO;
import com.example.project_sem_4.service.authen.AuthenticationService;
import com.example.project_sem_4.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VoucherController {
    private final VoucherService voucherService;
    @RequestMapping(value = "/voucher/create", method = RequestMethod.POST)
    public ResponseEntity<Object> saveVoucher(@RequestBody @Valid VoucherDTO model) {
        boolean result = voucherService.saveVoucher(model);
        if (result){
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.internalServerError().body(null);
    }
}
