package com.example.project_sem_4.database.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
@Table(name = "bookings")
public class Booking extends BaseEntity{
    @Id
    @GeneratedValue(generator = "my_generator")
    @GenericGenerator(name = "my_generator", strategy = "com.example.project_sem_4.database.generator.MyGenerator")
    private String id;
    private String name;
    private String date;
    private String email;
    private String phone;
    private int user_id;

    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(name = "employee_id")
    private Account employee;
    @Column(insertable = false, updatable = false)
    private int employee_id;

    @ManyToOne(fetch = FetchType.EAGER, cascade =
            {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @Column(insertable = false, updatable = false)
    private int branch_id;

    public Booking() {
        super();
    }
}
