package com.example.project_sem_4.seed;

import com.example.project_sem_4.database.entities.Booking;
import com.example.project_sem_4.database.entities.MembershipClass;
import com.example.project_sem_4.database.entities.Role;
import com.example.project_sem_4.database.repository.BookingRepository;
import com.example.project_sem_4.database.repository.MembershipClassRepository;
import com.example.project_sem_4.database.repository.RoleRepository;
import com.example.project_sem_4.enum_project.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedTest implements CommandLineRunner {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MembershipClassRepository membershipClassRepository;

    @Override
    public void run(String... args) throws Exception {
//        roleRepository.deleteAll();
//        roleRepository.save(Role.builder().name("admin").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        MembershipClass membershipClass = membershipClassRepository.findById(1).orElse(null);
//        if (membershipClass == null){
//            membershipClassRepository.save(MembershipClass.builder().name("Hạng S").build());
//            membershipClassRepository.save(MembershipClass.builder().name("Hạng A").build());
//            membershipClassRepository.save(MembershipClass.builder().name("Hạng B").build());
//            membershipClassRepository.save(MembershipClass.builder().name("Hạng C").build());
//            membershipClassRepository.save(MembershipClass.builder().name("Hạng D").build());

//            roleRepository.save(Role.builder().name(RoleEnum.ADMIN.role).build());
//            roleRepository.save(Role.builder().name(RoleEnum.RECEPTIONISTS.role).build());
//            roleRepository.save(Role.builder().name(RoleEnum.STAFF.role).build());
//            roleRepository.save(Role.builder().name(RoleEnum.CUSTOMER_CARE.role).build());
//            roleRepository.save(Role.builder().name(RoleEnum.CUSTOMER.role).build());
//        }
    }
}
