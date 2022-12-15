package com.example.project_sem_4.database.dto.search.account;

import com.example.project_sem_4.database.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountSearchDTO {
    private Integer accounts_id;
    private String accounts_name;
    private String accounts_email;
    private String phone;
    private String gender;
    private String address;
    private String thumbnail;
    private Double total_payment;
    private String account_created_at;
//    private Double roles_id;
//    private String roles_name;
//    private Set<Role> roles;
}
