package com.example.project_sem_4.database.search_body;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceSearchBody {
    private String name;
    private Integer typeServiceId;
    private Integer status;
    private String start;
    private String end;
    private Integer limit;
    private Integer page;
    private String sort;
}
