package com.example.project_sem_4.seed;
import com.example.project_sem_4.database.dto.ServiceDTO;
import com.example.project_sem_4.database.entities.*;
import com.example.project_sem_4.database.repository.*;
import com.example.project_sem_4.enum_project.GenderEnum;
import com.example.project_sem_4.enum_project.RoleEnum;
import com.example.project_sem_4.enum_project.StatusEnum;
import com.example.project_sem_4.enum_project.TimeBookingEnum;
import com.example.project_sem_4.service.authen.AuthenticationService;
import com.example.project_sem_4.service.authen.RoleService;
import com.example.project_sem_4.service.service.ServiceHair;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ServiceRepository serviceModelRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private OrderDetailRepository orderDetailRepository;




    @Override
    public void run(String... args) throws Exception {
        Random rand = new Random();
        Calendar cal = Calendar.getInstance();
        String sql;
        if (createSeed) {
            List<Branch> branches = branchRepository.findAll();
            if(branches.size() == 0){
                Integer[] statuss = {0, StatusEnum.ACTIVE.status};

                String hotLine = "0773776942";

                    String thumbnail1 = "https://lawkey.vn/wp-content/uploads/2016/10/72358PICV9G.jpg";
                    String address1 = "Hà Nội";
                    String name1 = "Cơ Sở Hoàng Quốc Việt";


                String thumbnail2 = "https://2doctor.org/wp-content/uploads/2021/08/dia-chi-cat-toc-nam-dep-o-ha-noi.jpg";
                String address2 = "Hồ Chí Minh";
                String name2 = "Cơ Sở Hoàng Quốc Việt";

                    Date dateBranch = getRamdomDate(2020, 2020, "yyyy-MM-dd");
                    Integer status = statuss[rand.nextInt(statuss.length)];
                    String bookingDateBranch = String.valueOf(dateBranch.getDate());
                    if (dateBranch.getDate() < 10) {
                        bookingDateBranch = 0 + String.valueOf(dateBranch.getDate());
                    }

                    int validateMonthBranch = dateBranch.getMonth() + 1;
                    String bookingMonthBranch = String.valueOf(validateMonthBranch);
                    if (validateMonthBranch < 10) {
                        bookingMonthBranch = 0 + String.valueOf(validateMonthBranch);
                    }

                    cal.setTime(dateBranch);
                    String bookingYearBranch = String.valueOf(cal.get(Calendar.YEAR));

                   String sql1 = "INSERT INTO branchs VALUES (" +
                       1 + ","+
                           '"' + bookingYearBranch + "-" + bookingMonthBranch + "-" + bookingDateBranch + '"' + ","+
                           status + ","+
                           '"' + bookingYearBranch + "-" + bookingMonthBranch + "-" + bookingDateBranch + '"' + ","+
                            '"' + address1+ '"' + ","+
                            '"' + hotLine+ '"' + ","+
                            '"' + name1+ '"' + ","+
                            '"' + thumbnail1+ '"' + ")"
                   ;
                   jdbcTemplate.update(
                           sql1);
                String sql2 = "INSERT INTO branchs VALUES (" +
                        2 + ","+
                        '"' + bookingYearBranch + "-" + bookingMonthBranch + "-" + bookingDateBranch + '"' + ","+
                        status + ","+
                        '"' + bookingYearBranch + "-" + bookingMonthBranch + "-" + bookingDateBranch + '"' + ","+
                        '"' + address2+ '"' + ","+
                        '"' + hotLine+ '"' + ","+
                        '"' + name2+ '"' + ","+
                        '"' + thumbnail2+ '"' + ")"
                        ;
                jdbcTemplate.update(
                        sql2);

            }

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

            Account Walk_In_Guest = accountRepository.findById(1).orElse(null);
            if (Walk_In_Guest == null) {
                authenticationService.saveWalk_In_Guest();
                createAccount("ADMIN");
                createAccount("RECEPTIONISTS");
                createAccount("STAFF");
                createAccount("CUSTOMER_CARE");
                createAccount("CUSTOMER");
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

            List<ServiceModel> services = serviceModelRepository.findAll();
        if (services.size() == 0){
            String[] link = {"https://i.pinimg.com/236x/47/ae/24/47ae2447e4cd688098398f6c8687bea0.jpg",
            "https://i.pinimg.com/236x/35/e5/a8/35e5a8cb6c8f31599b6cdff138ba13ef.jpg",
            "https://i.pinimg.com/236x/6c/93/d6/6c93d61f013b9e7ec3ea47f998574e7e.jpg",
            "https://i.pinimg.com/236x/83/4e/f6/834ef6a7e9cbd9388c3b2345af769ef3.jpg",
            "https://i.pinimg.com/236x/c3/ea/23/c3ea233f4cd95b9c6c9ee0bc0212d938.jpg"};
            Integer status = 1;
            String description = "Kiểu tóc nam,nữ đẹp ";
            String name = "Đầu cắt moi";
            double[] price = {200000,300000,400000,500000};
            Integer[] type = {1,2,3,4,5};
            for (int i = 1; i < 20; i++) {
                Date dateservice = getRamdomDate(2020, 2022, "yyyy-MM-dd");
                String linkz = link[rand.nextInt(link.length)];
                double pricez = price[rand.nextInt(price.length)];
                Integer typez = type[rand.nextInt(type.length)];

                String bookingDateService = String.valueOf(dateservice.getDate());
                if (dateservice.getDate() < 10) {
                    bookingDateService = 0 + String.valueOf(dateservice.getDate());
                }

                int validateMonthService = dateservice.getMonth() + 1;
                String bookingMonthService = String.valueOf(validateMonthService);
                if (validateMonthService < 10) {
                    bookingMonthService = 0 + String.valueOf(validateMonthService);
                }
                 cal = Calendar.getInstance();
                cal.setTime(dateservice);
                String bookingYearService = String.valueOf(cal.get(Calendar.YEAR));

                 sql = "INSERT INTO services VALUES (" +
                       i + ","+
                        '"' + bookingYearService + "-" + bookingMonthService + "-" + bookingDateService + '"' + ","+
                        status + ","+
                        '"' + bookingYearService + "-" + bookingMonthService + "-" + bookingDateService + '"' + ","+
                        '"'+description+'"' + ","+
                        '"'+name+'"' + ","+
                       pricez+ ","+
                        '"'+ linkz+'"'+ ","+
                        typez+")";
                jdbcTemplate.update(sql);
            }
        }

        List<Booking> booking = bookingRepository.findAll();
        if (booking.size() == 0) {
            Integer[] branch_ids = {1, 2};
//            Integer[] emp_ids = {2, 3, 4, 5, 6, 7, 8};
            Integer[] emp_ids = {4};
            Integer[] service_names = {1,2,3,4,5,6,7,8};

            TimeBookingEnum[] time_bookings = TimeBookingEnum.values();
//            Integer[] user_ids = {5};
            Integer[] user_ids = {1,6};
            Integer user_ids_zero = 0;
            Integer[] statuss = {-1, 0, 1, 2};

            for (int i = 0; i < 600; i++) {
                Integer status = statuss[rand.nextInt(statuss.length)];
                Date date = getRamdomDate(2020, 2022, "yyyy-MM-dd");
                Integer branch_id = branch_ids[rand.nextInt(branch_ids.length)];
                Integer emp_id = emp_ids[rand.nextInt(emp_ids.length)];
                Integer service_name = service_names[rand.nextInt(service_names.length)];
                String time_booking = time_bookings[rand.nextInt(time_bookings.length)].timeName;
                Integer user_id = user_ids[rand.nextInt(user_ids.length)];
                String email = "Seeder@gmail.com";

                String bookingDate = String.valueOf(date.getDate());
                if (date.getDate() < 10) {
                    bookingDate = 0 + String.valueOf(date.getDate());
                }

                int validateMonth = date.getMonth() + 1;
                String bookingMonth = String.valueOf(validateMonth);
                if (validateMonth < 10) {
                    bookingMonth = 0 + String.valueOf(validateMonth);
                }
                 cal = Calendar.getInstance();
                cal.setTime(date);
                String bookingYear = String.valueOf(cal.get(Calendar.YEAR));

                 sql = "INSERT INTO bookings (id,created_at,status,updated_at,branch_id,date,date_booking,email,employee_id,phone,time_booking,name_booking,user_id) VALUES (" +
                        '"' +  "HN" +i+ '"' + ","+
                        '"' + bookingYear + "-" + bookingMonth + "-" + bookingDate + '"' + ","+
                        status + ","+
                        '"' + bookingYear + "-" + bookingMonth + "-" + bookingDate + '"' + ","+
                        branch_id + ","+
                        '"' + String.valueOf(date.getTime()) + '"'  + ","+
                        '"' +bookingDate + "-" + bookingMonth + "-" + bookingYear + '"' + ","+
                        '"' +email+ '"' + ","+
                        emp_id + ","+
                        "''" + ","+
                         '"' + time_booking + '"' + ","+
                         service_name+ ","+
                         user_id + ")"
                        ;
                jdbcTemplate.update(
                        sql);
            }
            for (int i = 601; i < 631; i++) {
                Integer status = statuss[rand.nextInt(statuss.length)];
                Date date = getRamdomDate(2021, 2022, "yyyy-MM-dd");
                Integer branch_id = branch_ids[rand.nextInt(branch_ids.length)];
                Integer service_name = service_names[rand.nextInt(service_names.length)];
                Integer emp_id = emp_ids[rand.nextInt(emp_ids.length)];

                String time_booking = time_bookings[rand.nextInt(time_bookings.length)].timeName;
                Integer user_id = user_ids_zero;
                String email = "Seeder@gmail.com";

                String bookingDate = String.valueOf(date.getDate());
                if (date.getDate() < 10) {
                    bookingDate = 0 + String.valueOf(date.getDate());
                }

                int validateMonth = date.getMonth() + 1;
                String bookingMonth = String.valueOf(validateMonth);
                if (validateMonth < 10) {
                    bookingMonth = 0 + String.valueOf(validateMonth);
                }
                cal = Calendar.getInstance();
                cal.setTime(date);
                String bookingYear = String.valueOf(cal.get(Calendar.YEAR));

                sql = "INSERT INTO bookings (id,created_at,status,updated_at,branch_id,date,date_booking,email,employee_id,phone,time_booking,name_booking,user_id) VALUES (" +
                        '"' +  "HN" +i+ '"' + ","+
                        '"' + bookingYear + "-" + bookingMonth + "-" + bookingDate + '"' + ","+
                        status + ","+
                        '"' + bookingYear + "-" + bookingMonth + "-" + bookingDate + '"' + ","+
                        branch_id + ","+
                        '"' + String.valueOf(date.getTime()) + '"'  + ","+
                        '"' +bookingDate + "-" + bookingMonth + "-" + bookingYear + '"' + ","+
                        '"' +email+ '"' + ","+
                        emp_id + ","+
                        "''" + ","+
                        '"' + time_booking + '"' + ","+
                        service_name+ ","+
                        user_id + ")"
                ;
                jdbcTemplate.update(
                        sql);
            }
        }
        List<Order> orders = orderRepository.findAll();
        if (orders.size() == 0 && booking.size()> 0) {

            Integer[] statussOrder = {-1,0,1,2};
            Integer[] customer_ids = {1, 6};
            double[] pricesOrder = {200000,300000,400000,500000};

            for (int i = 1; i < 100; i++) {
                Date dateorder = getRamdomDate(2020, 2022, "yyyy-MM-dd");
                Booking bookin =  booking.get(rand.nextInt(booking.size()));
                String booking_id = bookin.getId();
                Integer customer_id = customer_ids[rand.nextInt(customer_ids.length)];
                Double priceOrder = pricesOrder[rand.nextInt(pricesOrder.length)];
                Integer statusOrder = statussOrder[rand.nextInt(statussOrder.length)];

                String bookingDateOrder = String.valueOf(dateorder.getDate());
                if (dateorder.getDate() < 10) {
                    bookingDateOrder = 0 + String.valueOf(dateorder.getDate());
                }

                int validateMonthOrder = dateorder.getMonth() + 1;
                String bookingMonthOrder = String.valueOf(validateMonthOrder);
                if (validateMonthOrder < 10) {
                    bookingMonthOrder = 0 + String.valueOf(validateMonthOrder);
                }
                 cal = Calendar.getInstance();
                cal.setTime(dateorder);
                String bookingYearOrder = String.valueOf(cal.get(Calendar.YEAR));

                 sql = "INSERT INTO orders VALUES (" +
                        i + ","+
                        '"' + bookingYearOrder + "-" + bookingMonthOrder + "-" + bookingDateOrder + '"' + ","+
                        statusOrder + ","+
                        '"' + bookingYearOrder + "-" + bookingMonthOrder + "-" + bookingDateOrder + '"' + ","+
                        '"'+booking_id+'"' + ","+
                        customer_id + ","+
                        priceOrder + ","+ "''" +
                          ")";
                jdbcTemplate.update(
                        sql);
            }
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        if(orderDetails.size() == 0 && orders.size() > 0 && services.size() > 0){
            for (int i = 1; i < 210; i++) {
                Order order =  orders.get(rand.nextInt(orders.size()));
                Integer order_id = order.getId();
                ServiceModel serviceModel = services.get(rand.nextInt(services.size()));
               Integer service_id = serviceModel.getId();
               double unit_price = serviceModel.getPrice();
                 sql = "SELECT count(*) FROM order_details WHERE order_id = ? AND service_id = ?";

                int count = jdbcTemplate.queryForObject(sql, new Object[] { order_id,service_id }, Integer.class);
                    if (count == 0){
                        sql = "INSERT INTO order_details VALUES (" +
                                order_id + ","+
                                service_id + ","+
                                unit_price +")";
                        jdbcTemplate.update(
                                sql);
                    }
                  }
              }


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

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public Date getRamdomDate(int yearFrom, int yearEnd, String format) throws ParseException {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(yearFrom, yearEnd);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        String created_at = gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
        //"yyyy-MM-dd"
        return new SimpleDateFormat(format).parse(created_at);
    }
}
