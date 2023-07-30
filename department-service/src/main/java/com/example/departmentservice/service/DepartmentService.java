package com.example.departmentservice.service;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = new Department(departmentDto.getId(), departmentDto.getDeptName(), departmentDto.getDeptDesc(), departmentDto.getDeptCode());
        Department saveDept = departmentRepository.save(department);
        DepartmentDto departmentDto1 = new DepartmentDto(saveDept.getId(), saveDept.getDeptName(), saveDept.getDeptDesc(), saveDept.getDeptCode());
        return departmentDto1;
    }

    public DepartmentDto fetchDepartmentByDeptCode(String deptCode) {
        Department department = departmentRepository.findByDeptCode(deptCode);
        return new DepartmentDto(department.getId(), department.getDeptName(), department.getDeptDesc(), department.getDeptCode());
    }
}
