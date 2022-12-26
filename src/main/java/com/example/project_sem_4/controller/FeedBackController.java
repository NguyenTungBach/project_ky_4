package com.example.project_sem_4.controller;

import com.example.project_sem_4.database.entities.FeedBack;
import com.example.project_sem_4.database.search_body.FeedBackSearchBody;
import com.example.project_sem_4.service.feedback.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/feedback")
@CrossOrigin(origins = "*")
public class FeedBackController {
    @Autowired
    FeedBackService feedBackService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody @Valid FeedBack feedBack) {
        return new ResponseEntity<>(feedBackService.create(feedBack), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity findAll(@RequestBody FeedBackSearchBody feedBackSearchBody) {
        return new ResponseEntity<>(feedBackService.findAll(feedBackSearchBody), HttpStatus.OK);
    }

    @GetMapping("/checkRead")
    public ResponseEntity checkRead(@RequestParam Integer id) {
        return new ResponseEntity<>(feedBackService.checkRead(id), HttpStatus.OK);
    }

    @GetMapping("/deleteRead")
    public ResponseEntity delete(@RequestParam Integer id) {
        return new ResponseEntity<>(feedBackService.deleteRead(id), HttpStatus.OK);
    }
}
