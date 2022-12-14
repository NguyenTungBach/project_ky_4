package com.example.project_sem_4.database.search_body;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchBody {
    private String booking_id;
    private Integer customer_id;
    private String voucher_id;
    private Integer rangeTotalPriceStart;
    private Integer rangeTotalPriceEnd;

    private Integer status;
    private String start;
    private String end;
    private Integer limit;
    private Integer page;
    private String sort;

    public OrderSearchBody() {
        this.limit = 4;
        this.page = 1;
    }
}
