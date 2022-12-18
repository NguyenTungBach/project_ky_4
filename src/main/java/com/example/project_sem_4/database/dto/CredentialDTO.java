package com.example.project_sem_4.database.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CredentialDTO {
    private String username;
    private String email;
    private Date created_at;
    private Date updated_at;
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
}
