package com.example.project_sem_4.database.repository;

import com.example.project_sem_4.database.entities.TypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeServiceRepository extends JpaRepository<TypeService, Integer> {
}
