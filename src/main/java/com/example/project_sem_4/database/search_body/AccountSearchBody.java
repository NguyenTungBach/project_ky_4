package com.example.project_sem_4.database.search_body;

import com.example.project_sem_4.database.entities.Role;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AccountSearchBody {
    private String name;
    private String email;
    private Integer role_id;
    private Integer member_ship_class_id;
    private String phone;
    private String gender;
    private Integer status;
    private String start;
    private String end;
    private Integer limit;
    private Integer page;
    private String sort;

    public AccountSearchBody() {
        this.role_id = -1;
        this.member_ship_class_id = -1;
        this.limit = 1;
        this.page = 4;
        this.status = -1;
        this.sort = "asc";
    }
}
