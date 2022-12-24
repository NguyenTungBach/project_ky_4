package com.example.project_sem_4.database.repository;

import com.example.project_sem_4.database.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
