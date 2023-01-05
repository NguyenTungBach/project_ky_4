package com.example.project_sem_4.service.order;

import com.example.project_sem_4.database.dto.booking.BookingSearchDTO;
import com.example.project_sem_4.database.dto.order.OrderDTO;
import com.example.project_sem_4.database.dto.order.OrderSearchDTO;
import com.example.project_sem_4.database.entities.*;
import com.example.project_sem_4.database.jdbc_query.QueryOrderByJDBC;
import com.example.project_sem_4.database.repository.*;
import com.example.project_sem_4.database.search_body.BookingSearchBody;
import com.example.project_sem_4.database.search_body.OrderSearchBody;
import com.example.project_sem_4.enum_project.StatusEnum;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionBadRequest;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private QueryOrderByJDBC queryOrderByJDBC;

    @Transactional
    public Order create(OrderDTO orderDTO){
        //Kiểm tra tài khoản
        Account checkCustomer = accountRepository.findById(orderDTO.getCustomer_id()).orElse(null);
        if (checkCustomer == null){
            throw new ApiExceptionNotFound("orders","customer_id",orderDTO.getCustomer_id());
        }
        //Kiểm tra voucher
        if (orderDTO.getVoucher_id() != null){
            Voucher voucher = voucherRepository.findByVoucherCode(orderDTO.getVoucher_id());
            if (voucher == null){
                throw new ApiExceptionNotFound("orders","voucher_id",orderDTO.getVoucher_id());
            }
            if (voucher.getExpired_date().after(new Date())){
                throw new ApiExceptionBadRequest("orders","voucher_id", "Voucher hết hạn" + orderDTO.getVoucher_id());
            }
        }
        //Kiểm tra mã Booking và trạng thái
        Booking checkBooking = bookingRepository.findById(orderDTO.getBooking_id()).orElse(null);
        if (checkBooking == null){
            throw new ApiExceptionNotFound("orders","booking_id",orderDTO.getBooking_id());
        }
        if (checkBooking.getStatus() == StatusEnum.ACTIVE.status){
            throw new ApiExceptionBadRequest("orders","booking_id","Lịch đã được đặt " + orderDTO.getBooking_id());
        }

        if (checkCustomer.getId() == 1){
            checkBooking.setStatus(StatusEnum.ACTIVE.status);
            checkBooking.setEmail(orderDTO.getEmail());
            checkBooking.setPhone(orderDTO.getPhone());
            checkBooking.setUser_id(orderDTO.getCustomer_id());
        } else {
            checkBooking.setStatus(StatusEnum.ACTIVE.status);
            checkBooking.setEmail(checkCustomer.getEmail());
            checkBooking.setPhone(checkCustomer.getPhone());
            checkBooking.setUser_id(checkCustomer.getId());
        }
        bookingRepository.save(checkBooking);

        Order order = orderRepository.save(Order.builder()
                .booking_id(orderDTO.getBooking_id())
                .customer(checkCustomer)
                .voucher_id(orderDTO.getVoucher_id())
                .build());
        order.setCreated_at(new Date());
        return orderRepository.save(order);
    }

    public Map<String, Object> findAll(OrderSearchBody searchBody) {
        List<OrderSearchDTO> listContentPage = queryOrderByJDBC.filterWithPaging(searchBody);
        List<OrderSearchDTO> listContentNoPage = queryOrderByJDBC.filterWithNoPaging(searchBody);

        Map<String, Object> responses = new HashMap<>();
        responses.put("content",listContentPage);
        responses.put("currentPage",searchBody.getPage());
        responses.put("totalItems",listContentNoPage.size());
        responses.put("totalPage",(int) Math.ceil((double) listContentNoPage.size() / searchBody.getLimit()));
        return responses;
    }

}
