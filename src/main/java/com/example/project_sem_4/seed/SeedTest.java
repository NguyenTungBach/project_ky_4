package com.example.project_sem_4.seed;

import com.example.project_sem_4.database.entities.*;
import com.example.project_sem_4.database.repository.*;
import com.example.project_sem_4.enum_project.RoleEnum;
import com.example.project_sem_4.enum_project.StatusEnum;
import com.example.project_sem_4.enum_project.TimeBookingEnum;
import com.example.project_sem_4.service.authen.AuthenticationService;
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
        String sql;
//        roleRepository.deleteAll();
//        roleRepository.save(Role.builder().name("admin").build());
//        bookingRepository.save(Booking.builder().name("test").build());
//        bookingRepository.save(Booking.builder().name("test").build());
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
            typeServiceRepository.save(new TypeService("Cắt Tóc", 1));
            typeServiceRepository.save(new TypeService("Massage mặt", 1));
            typeServiceRepository.save(new TypeService("Massage đầu", 1));
            typeServiceRepository.save(new TypeService("Vẩy light", 1));
            typeServiceRepository.save(new TypeService("Dưỡng da", 1));
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
                Date dateservice = getRamdomDate(2015, 2022, "yyyy-MM-dd");
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
                Calendar cal = Calendar.getInstance();
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
                jdbcTemplate.update(
                        sql);
            }
        }

        List<Booking> booking = bookingRepository.findAll();
        if (booking.size() == 0) {
            Integer[] branch_ids = {1, 2};
            Integer[] emp_ids = {2, 3, 4, 5, 6, 7, 8};
            TimeBookingEnum[] time_bookings = TimeBookingEnum.values();
            Integer[] user_ids = {1, 4};
            Integer[] statuss = {-1, 0, 1, 2};



            for (int i = 0; i < 200; i++) {
                Integer status = statuss[rand.nextInt(statuss.length)];
                Date date = getRamdomDate(2015, 2022, "yyyy-MM-dd");
                Integer branch_id = branch_ids[rand.nextInt(branch_ids.length)];
                Integer emp_id = emp_ids[rand.nextInt(emp_ids.length)];
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
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                String bookingYear = String.valueOf(cal.get(Calendar.YEAR));

                 sql = "INSERT INTO bookings VALUES (" +
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
                        user_id + ","+
                        "''" + ")"
                        ;
                jdbcTemplate.update(
                        sql);
            }

        }
        List<Order> orders = orderRepository.findAll();
        if (orders.size() == 0) {

            Integer[] statussOrder = {-1,0,1,2};
            Integer[] customer_ids = {1, 4};
            double[] pricesOrder = {200000,300000,400000,500000};
            Integer voicher_id = 0;
            for (int i = 1; i < 100; i++) {
                Date dateorder = getRamdomDate(2015, 2022, "yyyy-MM-dd");
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
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateorder);
                String bookingYearOrder = String.valueOf(cal.get(Calendar.YEAR));

                 sql = "INSERT INTO orders VALUES (" +
                        i + ","+
                        '"' + bookingYearOrder + "-" + bookingMonthOrder + "-" + bookingDateOrder + '"' + ","+
                        statusOrder + ","+
                        '"' + bookingYearOrder + "-" + bookingMonthOrder + "-" + bookingDateOrder + '"' + ","+
                        '"'+booking_id+'"' + ","+
                        customer_id + ","+
                        priceOrder + ","+
                        '"'+voicher_id+'"' +")";
                jdbcTemplate.update(
                        sql);
            }
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        if(orderDetails.size() == 0){


            for (int i = 1; i < 210; i++) {
                Date dateorderDetail = getRamdomDate(2015, 2022, "yyyy-MM-dd");
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
        Account Walk_In_Guest = accountRepository.findById(1).orElse(null);
        if (Walk_In_Guest == null) {
            authenticationService.saveWalk_In_Guest();
        }
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
