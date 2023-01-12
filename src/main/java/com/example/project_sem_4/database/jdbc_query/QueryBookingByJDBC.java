package com.example.project_sem_4.database.jdbc_query;

import com.example.project_sem_4.database.dto.booking.BookingSearchDTO;
import com.example.project_sem_4.database.entities.Booking;
import com.example.project_sem_4.database.entities.Role;
import com.example.project_sem_4.database.search_body.BookingSearchBody;
import com.example.project_sem_4.util.HelpConvertDate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Repository
public class QueryBookingByJDBC {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<BookingSearchDTO> filterWithPaging(BookingSearchBody searchBody) {
        return jdbcTemplate.query(stringQuery(searchBody)
                , new ResultSetExtractor<List<BookingSearchDTO>>() {
            @Override
            public List<BookingSearchDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<BookingSearchDTO>  ListEmpForBooking = new ArrayList<BookingSearchDTO>();
                Map<Integer,BookingSearchDTO.Employee> liszt = new HashMap<Integer, BookingSearchDTO.Employee>();
                Map<Integer, Map<String,Booking>> bookingsMaper = new HashMap<Integer, Map<String,Booking>>();
                Map<Integer, Map<Integer,Role>> roleMaper = new HashMap<Integer, Map<Integer,Role>>();
                int count = 1;
                while (rs.next()) {
                    count++;
                    // Lấy ra tài khoản nhân viên
                    Integer keyAccount = rs.getInt("accounts.id");

                    BookingSearchDTO.Employee employee = liszt.get(keyAccount);

                    if (employee == null) {
                        employee = new BookingSearchDTO.Employee();
                        employee.setEmployee_name(rs.getString("accounts.name"));
                        liszt.put(keyAccount, employee);
                    }

                    // Lấy ra danh sách lịch đặt theo account nếu booking.employee_id = accounts.id
                    Integer keyBookingEmployee = rs.getInt("bookings.employee_id");
                    Map<String,Booking> bookingList = bookingsMaper.get(keyBookingEmployee);
                    Booking booking = new Booking();
                    booking.setId(rs.getString("bookings.id"));
                    booking.setEmployee_id(rs.getInt("bookings.employee_id"));
                    booking.setDate_booking(rs.getString("bookings.date_booking"));
                    booking.setBranch_id(rs.getInt("bookings.branch_id"));
                    booking.setTime_booking(rs.getString("bookings.time_booking"));
                    if (bookingList == null ){
                        bookingList = new HashMap<>();
                    }
                    bookingList.put(booking.getId(),booking);
                    bookingsMaper.put(keyBookingEmployee,bookingList);
                    employee.setBookingByTime_bookings(new ArrayList<>(bookingList.values()));

                    Integer keyRolesEmployee = rs.getInt("accounts.id");
                    Map<Integer,Role> roles = roleMaper.get(keyRolesEmployee);
                    Role role = new Role();
                    role.setId(rs.getInt("roles.id"));
                    role.setName(rs.getString("roles.name"));

                    if (roles == null ){
                        roles = new HashMap<>();
                    }
                    roles.put(role.getId(),role);
                    roleMaper.put(keyRolesEmployee,roles);

                    employee.setRoles(new ArrayList<>(roles.values()));
                }

                for ( Map.Entry<Integer, BookingSearchDTO.Employee> emp : liszt.entrySet()) {
                    BookingSearchDTO brschDTO = new BookingSearchDTO(emp.getValue());

                    ListEmpForBooking.add(brschDTO);
                }

                return  ListEmpForBooking;
            }
        });
    }

    public List<BookingSearchDTO> filterWithNoPaging(BookingSearchBody searchBody) {
        return jdbcTemplate.query(stringQuery(searchBody), new BeanPropertyRowMapper<>(BookingSearchDTO.class));
    }

    public String stringQuery(BookingSearchBody searchBody) {
        StringBuilder sqlQuery = new StringBuilder("Select *");
        sqlQuery.append(" FROM bookings " +
                " Join accounts ON bookings.employee_id = accounts.id" +
                " Join accounts_roles ON accounts.id = accounts_roles.account_id " +
                " Join roles ON accounts_roles.role_id = roles.id");

        sqlQuery.append(" Where 1=1");

        if (searchBody.getBranch_id() != null) {
            sqlQuery.append(" AND bookings.branch_id = " + searchBody.getBranch_id());
        }

        if (searchBody.getEmployee_id() != null) {
            sqlQuery.append(" AND accounts.id = " + searchBody.getEmployee_id());
        }
        if (searchBody.getEmployee_name() != null && searchBody.getEmployee_name().length() > 0) {
            sqlQuery.append(" AND accounts.name LIKE '%" + searchBody.getEmployee_name() + "%'");
        }

        if (searchBody.getDate_booking() != null && searchBody.getDate_booking().length() > 0) {
            sqlQuery.append(" AND bookings.date_booking = '" + searchBody.getDate_booking() + "'");
        }

        if (searchBody.getTime_booking() != null && searchBody.getTime_booking().length() > 0) {
            sqlQuery.append(" AND bookings.time_booking = '" + searchBody.getTime_booking() + "'");
        }

        if (searchBody.getStatus() != null) {
            sqlQuery.append(" AND bookings.status = " + searchBody.getStatus());
        } else {
            sqlQuery.append(" AND bookings.status != -1");
        }

        if (searchBody.getStart() != null && searchBody.getStart().length() > 0) {
            LocalDate date = HelpConvertDate.convertStringToLocalDate(searchBody.getStart());
            sqlQuery.append(" AND bookings.created_at >= '" + date + " 00:00:00' ");
        }

        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0) {
            LocalDate date = HelpConvertDate.convertStringToLocalDate(searchBody.getEnd());
            sqlQuery.append(" AND bookings.created_at <= '" + date + " 23:59:59' ");
        }

        switch (searchBody.getSort()){
            case "asc":
                sqlQuery.append(" ORDER BY bookings.id ASC ");
                break;
            case "desc":
                sqlQuery.append(" ORDER BY bookings.id DESC ");
                break;
            case "time_bookingASC":
                sqlQuery.append(" ORDER BY bookings.time_booking ASC ");
                break;
            case "time_bookingDESC":
                sqlQuery.append(" ORDER BY bookings.time_booking DESC ");
                break;
            default:
                sqlQuery.append(" ORDER BY bookings.time_booking ASC ");
                break;
        }
        log.info("check query: " + sqlQuery);
        return sqlQuery.toString();
    }
}
