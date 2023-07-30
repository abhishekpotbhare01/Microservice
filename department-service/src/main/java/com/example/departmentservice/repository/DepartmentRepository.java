package com.example.departmentservice.repository;

import com.example.departmentservice.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.StringTokenizer;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

     Department findByDeptCode(String deptCode);
}
