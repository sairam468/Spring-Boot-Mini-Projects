package com.sit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Long> {
}
