package com.example.project_sem_4.controller;

import com.example.project_sem_4.service.mail.mail_comfirm.MailConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MailController {
    @Autowired
    MailConfirmService mailConfirmService;

}
