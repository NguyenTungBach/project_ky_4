package com.example.project_sem_4.database.jdbc_query;

import com.example.project_sem_4.database.dto.booking.BookingSearchDTO;
import com.example.project_sem_4.database.dto.order.OrderSearchDTO;
import com.example.project_sem_4.database.entities.*;
import com.example.project_sem_4.database.search_body.BookingSearchBody;
import com.example.project_sem_4.database.search_body.OrderSearchBody;
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
public class QueryOrderByJDBC {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<OrderSearchDTO> filterWithPaging(OrderSearchBody searchBody) {
        return jdbcTemplate.query(stringQuery(searchBody) + " LIMIT "
                        + searchBody.getLimit() + " OFFSET " + searchBody.getLimit() * (searchBody.getPage()-1)
                , new ResultSetExtractor<List<OrderSearchDTO>>() {
                    @Override
                    public List<OrderSearchDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        List<OrderSearchDTO>  list = new ArrayList<OrderSearchDTO>();

                        while (rs.next()) {
                            OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
                            Integer order_id = rs.getInt("orders.id");
                            Integer total_price = rs.getInt("orders.total_price");
                            Integer status = rs.getInt("orders.status");
                            Integer user_id = rs.getInt("bookings.user_id");
                            orderSearchDTO.setId(order_id);
                            orderSearchDTO.setTotal_price(total_price);
                            orderSearchDTO.setStatus(status);
                            orderSearchDTO.setUser_id(user_id);

                            Booking booking = new Booking();
                            booking.setId(rs.getString("bookings.id"));
                            booking.setDate(rs.getString("bookings.date"));
                            booking.setDate_booking(rs.getString("bookings.date_booking"));
                            booking.setTime_booking(rs.getString("bookings.time_booking"));
                            booking.setPhone(rs.getString("bookings.phone"));
                            booking.setUser_id(rs.getInt("bookings.user_id"));
                            booking.setStatus(rs.getInt("bookings.status"));
                            booking.setEmail(rs.getString("bookings.email"));
                            booking.setBranch_id(rs.getInt("bookings.branch_id"));
                            orderSearchDTO.setBooking(booking);

                            Branch branch = new Branch();
                            branch.setId(rs.getInt("branchs.id"));
                            branch.setName(rs.getString("branchs.name"));
                            branch.setAddress(rs.getString("branchs.address"));
                            branch.setStatus(rs.getInt("branchs.status"));
                            branch.setHot_line(rs.getString("branchs.hot_line"));
                            booking.setBranch(branch);

                            Account customer = new Account();
                            customer.setId(rs.getInt("accounts.id"));
                            customer.setEmail(rs.getString("accounts.email"));
                            orderSearchDTO.setCustomer(customer);

                            if (rs.getString("vouchers.id") != null){
                                Voucher voucher = Voucher.builder()
                                        .voucherCode(rs.getString("vouchers.voucher_code"))
                                        .discount(rs.getDouble("vouchers.discount"))
                                        .build();
                                orderSearchDTO.setVoucher(voucher);
                            }
                            list.add(orderSearchDTO);
                        }

                        return list;
                    }
                });
    }

    public List<OrderSearchDTO> filterWithNoPaging(OrderSearchBody searchBody) {
        return jdbcTemplate.query(stringQuery(searchBody), new ResultSetExtractor<List<OrderSearchDTO>>() {
            @Override
            public List<OrderSearchDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<OrderSearchDTO>  list = new ArrayList<OrderSearchDTO>();

                while (rs.next()) {
                    OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
                    Integer order_id = rs.getInt("orders.id");
                    Integer total_price = rs.getInt("orders.total_price");
                    Integer status = rs.getInt("orders.status");
                    Integer user_id = rs.getInt("bookings.user_id");
                    orderSearchDTO.setId(order_id);
                    orderSearchDTO.setTotal_price(total_price);
                    orderSearchDTO.setStatus(status);
                    orderSearchDTO.setUser_id(user_id);

                    Booking booking = new Booking();
                    booking.setId(rs.getString("bookings.id"));
                    booking.setDate(rs.getString("bookings.date"));
                    booking.setDate_booking(rs.getString("bookings.date_booking"));
                    booking.setTime_booking(rs.getString("bookings.time_booking"));
                    booking.setPhone(rs.getString("bookings.phone"));
                    booking.setUser_id(rs.getInt("bookings.user_id"));
                    booking.setStatus(rs.getInt("bookings.status"));
                    booking.setEmail(rs.getString("bookings.email"));
                    booking.setBranch_id(rs.getInt("bookings.branch_id"));
                    orderSearchDTO.setBooking(booking);

                    Branch branch = new Branch();
                    branch.setId(rs.getInt("branchs.id"));
                    branch.setName(rs.getString("branchs.name"));
                    branch.setAddress(rs.getString("branchs.address"));
                    branch.setStatus(rs.getInt("branchs.status"));
                    branch.setHot_line(rs.getString("branchs.hot_line"));
                    booking.setBranch(branch);

                    Account customer = new Account();
                    customer.setId(rs.getInt("accounts.id"));
                    customer.setEmail(rs.getString("accounts.email"));
                    orderSearchDTO.setCustomer(customer);

                    if (rs.getString("vouchers.id") != null){
                        Voucher voucher = Voucher.builder()
                                .voucherCode(rs.getString("vouchers.voucher_code"))
                                .discount(rs.getDouble("vouchers.discount"))
                                .build();
                        orderSearchDTO.setVoucher(voucher);
                    }
                    list.add(orderSearchDTO);
                }

                return list;
            }
        });
    }

    public String stringQuery(OrderSearchBody searchBody) {
        StringBuilder sqlQuery = new StringBuilder("Select orders.*, bookings.*,  accounts.*, branchs.*, vouchers.*");
        sqlQuery.append(" FROM orders " +
                " JOIN bookings ON orders.booking_id = bookings.id " +
                " JOIN accounts ON orders.customer_id = accounts.id " +
                " JOIN branchs ON bookings.branch_id = branchs.id " +
                " LEFT JOIN vouchers ON orders.voucher_id = vouchers.id ");

        sqlQuery.append(" Where 1=1");

        if (searchBody.getBooking_id() != null) {
            sqlQuery.append(" AND orders.booking_id = " + searchBody.getBooking_id());
        }

        if (searchBody.getCustomer_id() != null) {
            sqlQuery.append(" AND orders.customer_id = " + searchBody.getCustomer_id());
        }

        if (searchBody.getRangeTotalPriceStart() != null) {
            if (searchBody.getRangeTotalPriceEnd() != null) {
                sqlQuery.append(" AND orders.total_price >= " + searchBody.getRangeTotalPriceStart());
                sqlQuery.append(" AND orders.total_price <= " + searchBody.getRangeTotalPriceEnd());
            } else {
                sqlQuery.append(" AND orders.total_price = " + searchBody.getRangeTotalPriceStart());
            }
        }

        if (searchBody.getStatus() != null) {
            sqlQuery.append(" AND orders.status = " + searchBody.getStatus());
        } else {
            sqlQuery.append(" AND orders.status != -1");
        }

        if (searchBody.getStart() != null && searchBody.getStart().length() > 0) {
            LocalDate date = HelpConvertDate.convertStringToLocalDate(searchBody.getStart());
            sqlQuery.append(" AND orders.created_at >= '" + date + " 00:00:00' ");
        }

        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0) {
            LocalDate date = HelpConvertDate.convertStringToLocalDate(searchBody.getEnd());
            sqlQuery.append(" AND orders.created_at <= '" + date + " 23:59:59' ");
        }

        sqlQuery.append(" ORDER BY orders.id ");
        if (searchBody.getSort() != null && searchBody.getSort().equals("asc")) {
            sqlQuery.append(" ASC ");
        }
        if (searchBody.getSort() != null && searchBody.getSort().equals("desc")) {
            sqlQuery.append(" DESC ");
        }
        return sqlQuery.toString();
    }
}
