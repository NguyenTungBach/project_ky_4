package com.example.project_sem_4.database.repository;

import com.example.project_sem_4.database.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,String> {

}
