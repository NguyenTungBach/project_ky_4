package com.example.project_sem_4.seed;

import com.example.project_sem_4.database.entities.Booking;
import com.example.project_sem_4.database.entities.Role;
import com.example.project_sem_4.database.repository.BookingRepository;
import com.example.project_sem_4.database.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedTest implements CommandLineRunner {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
//        roleRepository.deleteAll();
//        roleRepository.save(Role.builder().name("admin").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());


    }
}
