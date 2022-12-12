package com.example.project_sem_4.database.repository;

import com.example.project_sem_4.database.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
