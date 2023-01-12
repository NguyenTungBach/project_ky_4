package com.example.project_sem_4.seed;

import com.example.project_sem_4.database.dto.ServiceDTO;
import com.example.project_sem_4.database.entities.*;
import com.example.project_sem_4.database.repository.*;
import com.example.project_sem_4.enum_project.GenderEnum;
import com.example.project_sem_4.enum_project.RoleEnum;
import com.example.project_sem_4.enum_project.StatusEnum;
import com.example.project_sem_4.service.authen.AuthenticationService;
import com.example.project_sem_4.service.authen.RoleService;
import com.example.project_sem_4.service.service.ServiceHair;
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
    @Autowired
    RoleService roleService;
    @Autowired
    ServiceHair serviceHair;

    private boolean createSeed = true;

    @Override
    public void run(String... args) throws Exception {
//        roleRepository.deleteAll();
//        roleRepository.save(Role.builder().name("admin").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());
        if (createSeed) {
            MembershipClass membershipClass = membershipClassRepository.findById(1).orElse(null);
            if (membershipClass == null) {
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

            List<TypeService> typeServices = typeServiceRepository.findAll();
            if (typeServices.size() == 0) {
                typeServiceRepository.save(new TypeService(1,"Cắt Tóc", 1));
                typeServiceRepository.save(new TypeService(2,"Gội đầu", 1));
                typeServiceRepository.save(new TypeService(3,"Massage", 1));
                typeServiceRepository.save(new TypeService(4,"Nhuộm tóc", 1));
                typeServiceRepository.save(new TypeService(5,"Chăm sóc da", 1));

                serviceHair.createService(ServiceDTO.builder().name("Uốn Hàn Quốc 8 cấp độ")
                        .description("Thuốc uốn đầu tiên trên thế giới loại bỏ thành phần Hydrochloride khỏi Cysteamine không gây hại da đầu, cam kết không gây rụng tóc.")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/Service/uon-han-quoc/ep-side.jpg")
                        .typeServiceId(1).price(300000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Nhuộm màu thời trang")
                        .description("Nhuộm màu thời trang\n" +
                                "Bảng màu mới chia làm 4 gói nhuộm theo tone màu khác nhau phù hợp với từng đối tượng đặc biệt: Elegant Black, Modern Man, Lady Killah và Fashionisto\n" +
                                "Với gói màu Modern Man này, 30Shine muốn hướng tới một màu đen classic, đem đến sự thanh lịch, tút lại phong độ cho người đàn ông Việt Nam")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/landingpage/nhuom/nau/30shine-Shinecolor-nhuom-nau-cao-cap-dinh-hinh-nguoi-dan-ong-hien-dai-2.jpg")
                        .typeServiceId(4).price(350000.0)
                        .build());

                serviceHair.createService(ServiceDTO.builder().name("Massage cổ vai gáy")
                        .description("Massage cổ vai gáy cam ngọt, chín tầng mây")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/Service/tat-ca-dich-vu/20220105-massage-co-vai-gay.jpg")
                        .typeServiceId(3).price(400000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Lột mụn đầu đen")
                        .description("Combo lột mun đầu đen full face, đắp mặt nạ, tẩy da chết.")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/Service/tat-ca-dich-vu/30shine-lot-mun-cam-3.jpg?v=2")
                        .typeServiceId(5).price(85000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Chăm sóc cấp thiết, ultra white")
                        .description("Đắp mặt nạ lạnh, tẩy da chết sủi.")
                        .thumbnail("https://storage.30shine.com/ResourceWeb/data/images/Service/tat-ca-dich-vu/30shine-detox-3.jpg?v=2https://storage.30shine.com/ResourceWeb/data/images/Service/tat-ca-dich-vu/mat-na-sui-bot-tay-da-chet-3.jpg?v=2")
                        .typeServiceId(5).price(50000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Gội đầu dưỡng sinh Đài Loan")
                        .description("Gội đầu dưỡng sinh Đài Loan là phương pháp gội đầu nhằm làm sạch tóc và da đầu và thư giãn với kỹ thuật massage, day ấn tập trung vào các huyệt đạo giúp hệ thần kinh, hệ tuần hoàn bạch huyết được lưu thông, độc tố được đào thải, cơ thể được thư giãn và sức khỏe được tăng cường.")
                        .thumbnail("https://perlaspa.com.vn/wp-content/uploads/2021/01/goi-dau-duong-sinh-2.jpg")
                        .typeServiceId(2).price(300000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Gội đầu Detox tinh chất muối biển")
                        .description("Sử dụng muối biển sâu tự nhiên, vô trùng độ tinh khiết 100% giúp làm sạch sâu da đầu, sát khuẩn nhẹ loại bỏ hoàn toàn lớp dầu thừa, phần da chết trên da đầu.")
                        .thumbnail("https://amoon.vn/wp-content/uploads/2020/02/dich-vu-goi-dau.png")
                        .typeServiceId(2).price(150000.0)
                        .build());
                serviceHair.createService(ServiceDTO.builder().name("Cut Luxury")
                        .description("Tư vấn skincare, xông hơi mặt, cắt xả và tạo kiểu tóc bằng các sản phẩm cao cấp.")
                        .thumbnail("http://luxuryman.vn/Content/image/article.jpg")
                        .typeServiceId(1).price(200000.0)
                        .build());

            }
            createAccount("ADMIN");
            createAccount("RECEPTIONISTS");
            createAccount("STAFF");
            createAccount("CUSTOMER_CARE");
            createAccount("CUSTOMER");
        }
    }

    private void createAccount(String roleName){
        MembershipClass membershipClass = membershipClassRepository.findById(5).orElse(null);

        Set<Role> roles = new HashSet<>();
        Role role = roleService.getRole(roleName);
        roles.add(role);

        Account account = new Account();
        String name;
        String email;
        String phone;
        switch (roleName) {
            case "ADMIN":
                name = "Admin";
                email = "admin@gmail.com";
                phone = "0123523532";
                break;
            case "RECEPTIONISTS":
                name = "Receptionists";
                email = "receptionists@gmail.com";
                phone = "43241414141532";
                break;
            case "STAFF":
                name = "Staff";
                email = "staff@gmail.com";
                phone = "0214124142";
                break;
            case "CUSTOMER_CARE":
                name = "Customer Care";
                email = "customer_care@gmail.com";
                phone = "543564312";
                break;
            case "CUSTOMER":
                name = "Customer";
                email = "customer@gmail.com";
                phone = "464314141";
                break;
            default:
                name = "";
                email = "";
                phone = "";
                break;
        }
        account.setName(name);
        account.setEmail(email);
        account.setAddress("From No Where");
        account.setPhone(phone);
        account.setPassword(passwordEncoder.encode("12345678"));
        account.setGender(GenderEnum.MALE.gender);
        account.setMembershipClass(membershipClass);
        account.setRoles(roles);
        account.setCreated_at(new Date());
        account.setStatus(StatusEnum.ACTIVE.status);
        accountRepository.save(account);
    }
}
