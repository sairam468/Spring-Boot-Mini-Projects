package com.sit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sit.entity.StudentEnqEntity;

public interface StudentEnqrepo extends JpaRepository<StudentEnqEntity, Integer>, JpaSpecificationExecutor<StudentEnqEntity>{

}
