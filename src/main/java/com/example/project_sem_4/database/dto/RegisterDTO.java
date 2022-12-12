package com.example.project_sem_4.database.dto;

import com.example.project_sem_4.database.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @Email(message = "Sai định dạng email")
    @NotEmpty(message = "Thiếu email")
    private String email;
    @NotEmpty(message = "Thiếu password")
    private String password;
    @NotEmpty(message = "Thiếu role")
    private Set<Role> roles;
}
