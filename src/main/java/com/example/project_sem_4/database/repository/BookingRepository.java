package com.example.project_sem_4.database.repository;

import com.example.project_sem_4.database.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,String> {

    @Query(value = "Select" +
            " bookings.*"+
            " From bookings"+
            " WHERE bookings.date = ?1"+
            " AND bookings.employee_id = ?2"
            ,nativeQuery = true)
    Booking findByDateAndAndEmployee_id(String dateString, int Employee_id);
}
