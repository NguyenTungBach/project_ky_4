package com.example.project_sem_4.database.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int voucher_id;
    private int booking_id;
    private double total_price;
    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private Account customer;
    @Column(insertable = false, updatable = false)
    private int account_id;

    public Order() {
        super();
    }
}
