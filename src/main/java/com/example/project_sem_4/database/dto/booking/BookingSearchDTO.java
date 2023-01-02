package com.example.project_sem_4.database.dto.booking;

import com.example.project_sem_4.database.entities.Booking;
import com.example.project_sem_4.database.entities.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingSearchDTO {
    private String branch_id;
    private Employee employee;

    @Getter
    @Setter
    public static class Employee{
        private String employee_name;
        private List<Role> roles;

        private List<Booking> BookingByTime_bookings;

        public Employee() {
        }
    }
}
