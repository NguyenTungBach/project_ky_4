package com.example.project_sem_4.controller;

import com.example.project_sem_4.database.dto.BranchDTO;
import com.example.project_sem_4.database.entities.Branch;
import com.example.project_sem_4.database.repository.ResponeRepository;
import com.example.project_sem_4.service.branch.BranchService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.pool.TypePool;

import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin()
public class BranchController {
    private final BranchService branchService;
    @RequestMapping(value = "/branch/create-branch", method = RequestMethod.POST)
    //@RequestBody @Valid BranchDTO branchDTO
    public ResponseEntity<Object> create(@RequestBody @Valid BranchDTO branchDTO) {
        Branch branch = branchService.saveBranch(branchDTO);
        if (branch == null){
            return  ResponseEntity.ok().body(
                    ResponeRepository.ResponeJsonError("Tạo thất bại"));
        }
        return  ResponeRepository.ResponeJsonSusscess("Tạo thành công", branch.toJson());
    }

    @RequestMapping(value = "/branch/update-branch", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody @Valid BranchDTO branchDTO) {

        return ResponseEntity.ok().body(branchDTO);
    }
}
