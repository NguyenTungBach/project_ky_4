package com.example.project_sem_4.service.order;

import com.example.project_sem_4.database.dto.order.OrderDetailDTO;
import com.example.project_sem_4.database.entities.*;
import com.example.project_sem_4.database.repository.*;
import com.example.project_sem_4.enum_project.StatusEnum;
import com.example.project_sem_4.service.mail.mail_order_booking.MailOrderBooking;
import com.example.project_sem_4.service.mail.mail_order_detail.MailOrderDetail;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionBadRequest;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionCustomBadRequest;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionCustomNotFound;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionNotFound;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Log4j2
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private MailOrderDetail mailOrderDetail;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MailOrderBooking mailOrderBooking;

    @Transactional
    public Order create(OrderDetailDTO orderDetailDTO){
        Order order = orderRepository.findById(orderDetailDTO.getOrder_id()).orElse(null);
        if (order == null){
//            throw new ApiExceptionNotFound("orders","id",orderDetailDTO.getOrder_id());
            throw new ApiExceptionCustomNotFound("Không tìm thấy đơn hàng có mã là: " + orderDetailDTO.getOrder_id());
        }
        Double total_price = 0.0;
        for (OrderDetailDTO.ListServiceDetailDTO serviceDetailDTO: orderDetailDTO.getOrderDetails()) {
            ServiceModel serviceModel = serviceRepository.findById(serviceDetailDTO.getService_id()).orElse(null);
            if (serviceModel == null){
                throw new ApiExceptionNotFound("services","id",serviceDetailDTO.getService_id());
            }
            total_price += serviceDetailDTO.getUnit_price();
            OrderDetail orderDetail = OrderDetail.builder()
                    .pk(new OrderDetail.PK(orderDetailDTO.getOrder_id(),serviceDetailDTO.getService_id()))
                    .unit_price(serviceDetailDTO.getUnit_price())
                    .build();
            orderDetailRepository.save(orderDetail);
        }

        if (order.getVoucher_id() != null && order.getVoucher_id().length() > 0){
            Voucher voucher = voucherRepository.findByVoucherCode(order.getVoucher_id());
            if (voucher == null){
//                throw new ApiExceptionNotFound("orders","voucher_id",order.getVoucher_id());
                throw new ApiExceptionCustomNotFound("Không tìm thấy voucher có mã là: " + order.getVoucher_id());
            }
            if (voucher.getExpired_date().before(new Date())){
//                throw new ApiExceptionBadRequest("orders","voucher_id", "Voucher hết hạn" + order.getVoucher_id());
                throw new ApiExceptionCustomBadRequest("Voucher hết hạn có mã là: " + order.getVoucher_id());
            }
            if (voucher.is_used()){
//                throw new ApiExceptionBadRequest("orders","voucher_id", "Voucher đã được sử dụng " + order.getVoucher_id());
                throw new ApiExceptionCustomBadRequest("Voucher đã được sử dụng có mã là: " + order.getVoucher_id());
            }
            total_price = total_price - (total_price * voucher.getDiscount());
            voucher.set_used(true);
        }
        order.setTotal_price(total_price);
        order.setStatus(StatusEnum.ACTIVE.status);
        Order orderSave =orderRepository.save(order);
        Booking booking = bookingRepository.findById(order.getBooking_id()).orElse(null);
        if (booking == null){
//            throw new ApiExceptionNotFound("bookings","id",order.getBooking_id());
            throw new ApiExceptionCustomNotFound("Không tìm thấy ngày đặt lịch có mã là: "+order.getBooking_id());
        }
        String emailCustomer = booking.getEmail();
        Gson gson = new Gson();
        mailOrderDetail.sendMailOrderDetail(emailCustomer,gson.toJson(orderDetailDTO));

        Date date = new Date();
        String email = booking.getEmail();
        String dateHour = String.valueOf(date.getHours());
        String dateMinute = String.valueOf(date.getMinutes());
        mailOrderBooking.sendMailOrderBooking(order.getCustomer().getName(),email,dateHour+"h"+dateMinute);
        return orderSave;
    }
}
