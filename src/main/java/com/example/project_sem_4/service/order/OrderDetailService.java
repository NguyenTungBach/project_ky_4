package com.example.project_sem_4.service.order;

import com.example.project_sem_4.database.dto.order.OrderDetailDTO;
import com.example.project_sem_4.database.entities.Order;
import com.example.project_sem_4.database.entities.OrderDetail;
import com.example.project_sem_4.database.entities.ServiceModel;
import com.example.project_sem_4.database.repository.OrderDetailRepository;
import com.example.project_sem_4.database.repository.OrderRepository;
import com.example.project_sem_4.database.repository.ServiceRepository;
import com.example.project_sem_4.util.exception_custom_message.ApiExceptionNotFound;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Log4j2
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Transactional
    public Order create(OrderDetailDTO orderDetailDTO){
        Order order = orderRepository.findById(orderDetailDTO.getOrder_id()).orElse(null);
        if (order == null){
            throw new ApiExceptionNotFound("orders","id",orderDetailDTO.getOrder_id());
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
        order.setTotal_price(total_price);
        return orderRepository.save(order);
    }
}
