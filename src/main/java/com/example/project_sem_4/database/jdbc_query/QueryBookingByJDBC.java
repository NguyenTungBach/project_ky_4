package com.example.project_sem_4.database.jdbc_query;

import com.example.project_sem_4.database.dto.booking.BookingSearchDTO;
import com.example.project_sem_4.database.entities.Account;
import com.example.project_sem_4.database.entities.Blog;
import com.example.project_sem_4.database.entities.Booking;
import com.example.project_sem_4.database.entities.Role;
import com.example.project_sem_4.database.search_body.BlogSearchBody;
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

//    public List<BookingSearchDTO> filterWithPaging(BookingSearchBody searchBody) {
//        return jdbcTemplate.query(stringQuery(searchBody) + " LIMIT "
//                + searchBody.getLimit() + " OFFSET " + searchBody.getLimit() * (searchBody.getPage() - 1), new ResultSetExtractor<List<BookingSearchDTO>>() {
//            @Override
//            public List<BookingSearchDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
//                Map<Integer,BookingSearchDTO.Employee> list = new HashMap<Integer, BookingSearchDTO.Employee>();
//
//
//                List<Role> roleList = new ArrayList<>();
//
//                Map<Integer, List<Booking>> bookingsMaper = new HashMap<Integer, List<Booking>>();
//                Map<Integer, Role> roleMaper = new HashMap<Integer, Role>();
//                while (rs.next()) {
//                    // Lấy ra tài khoản nhân viên
//                    Integer keyAccount = rs.getInt("accounts.id");
//
//                    BookingSearchDTO.Employee employee = list.get(keyAccount);
//
//                    if (employee == null) {
//                        employee.setEmployee_name(rs.getString("accounts.name"));
//                        list.put(keyAccount, employee);
//                    }
//
//                    // Lấy ra danh sách lịch đặt theo account nếu booking.employee_id = accounts.id
//                    String keyBookingEmployee = rs.getString("bookings.employee_id");
//                    List<Booking> bookingList = bookingsMaper.get(keyBookingEmployee);
//                    if (bookingList == null ){
//                        Booking booking = new Booking();
//                        booking.setId(rs.getString("bookings.id"));
//                        booking.setEmployee_id(rs.getInt("bookings.employee_id"));
//                        booking.setDate_booking(rs.getString("bookings.date_booking"));
//                        booking.setTime_booking(rs.getString("bookings.time_booking"));
//
//                        //Kiểm tra tài khoản nhân viên có tồn tại trong booking này không
//                        bookingList.add(booking);
//                    }
//
//                    bookingsMaper.put(keyBookingEmployee,bookingList);
//                }
//                return list;
//            }
//        });
//    }

    public List<BookingSearchDTO> filterWithNoPaging(BookingSearchBody searchBody) {
        return jdbcTemplate.query(stringQuery(searchBody), new BeanPropertyRowMapper<>(BookingSearchDTO.class));
    }

    public String stringQuery(BookingSearchBody searchBody) {
        StringBuilder sqlQuery = new StringBuilder("Select *");
        sqlQuery.append(" FROM bookings ");

//        if (searchBody.getBranch_id() != null){
//            sqlQuery.append(" Join branchs ON bookings.branch_id = branchs.id ");
//        }

        if (searchBody.getEmployee_id() != null){
            sqlQuery.append(" Join accounts ON bookings.employee_id = accounts.id ");
        }

        if (searchBody.getRole() != null && searchBody.getRole().length() > 0){
            sqlQuery.append(" Join accounts_roles ON accounts.id = accounts_roles.account_id ");
            sqlQuery.append(" Join roles ON accounts_roles.role_id = roles.id ");
        }

        sqlQuery.append("Where 1=1");

        if (searchBody.getBranch_id() != null) {
            sqlQuery.append(" AND bookings.branch_id = " + searchBody.getBranch_id());
        }

        if (searchBody.getEmployee_id() != null) {
            sqlQuery.append(" AND accounts.id = " + searchBody.getEmployee_id());
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

        sqlQuery.append(" ORDER BY bookings.id ");
        if (searchBody.getSort() != null && searchBody.getSort().equals("asc")) {
            sqlQuery.append(" ASC ");
        }
        if (searchBody.getSort() != null && searchBody.getSort().equals("desc")) {
            sqlQuery.append(" DESC ");
        }
        log.info("check query: " + sqlQuery);
        return sqlQuery.toString();
    }
}
