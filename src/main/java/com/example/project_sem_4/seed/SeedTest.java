package com.example.project_sem_4.seed;

import com.example.project_sem_4.database.dto.RegisterCustomerDTO;
import com.example.project_sem_4.database.entities.*;
import com.example.project_sem_4.database.repository.*;
import com.example.project_sem_4.enum_project.GenderEnum;
import com.example.project_sem_4.enum_project.RoleEnum;
import com.example.project_sem_4.enum_project.StatusEnum;
import com.example.project_sem_4.service.authen.AuthenticationService;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SeedTest implements CommandLineRunner {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    MembershipClassRepository membershipClassRepository;

    @Autowired
    TypeServiceRepository typeServiceRepository;

    @Override
    public void run(String... args) throws Exception {
//        roleRepository.deleteAll();
//        roleRepository.save(Role.builder().name("admin").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());
        MembershipClass membershipClass = membershipClassRepository.findById(1).orElse(null);
        if (membershipClass == null){
            membershipClassRepository.save(MembershipClass.builder().name("Hạng S").build());
            membershipClassRepository.save(MembershipClass.builder().name("Hạng A").build());
            membershipClassRepository.save(MembershipClass.builder().name("Hạng B").build());
            membershipClassRepository.save(MembershipClass.builder().name("Hạng C").build());
            membershipClassRepository.save(MembershipClass.builder().name("Hạng D").build());

            roleRepository.save(Role.builder().name(RoleEnum.ADMIN.role).build());
            roleRepository.save(Role.builder().name(RoleEnum.RECEPTIONISTS.role).build());
            roleRepository.save(Role.builder().name(RoleEnum.STAFF.role).build());
            roleRepository.save(Role.builder().name(RoleEnum.CUSTOMER_CARE.role).build());
            roleRepository.save(Role.builder().name(RoleEnum.CUSTOMER.role).build());
        }

        List<TypeService> typeServices= typeServiceRepository.findAll();
        if (typeServices.size() == 0){
            typeServiceRepository.save(new TypeService("Cắt Tóc",1));
            typeServiceRepository.save(new TypeService("Massage mặt",1));
            typeServiceRepository.save(new TypeService("Massage đầu",1));
            typeServiceRepository.save(new TypeService("Vẩy light",1));
            typeServiceRepository.save(new TypeService("Dưỡng da",1));
        }

        Account Walk_In_Guest = accountRepository.findById(1).orElse(null);
        if (Walk_In_Guest == null){
            authenticationService.saveWalk_In_Guest();
        }
    }
}
