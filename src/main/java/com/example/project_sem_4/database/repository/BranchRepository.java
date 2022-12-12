package com.example.project_sem_4.database.repository;

import com.example.project_sem_4.database.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
