package com.example.departmentservice.controller;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dept/")
public class DeptController {
    private DepartmentService departmentService;

    public DeptController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto departmentDto1 = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity(departmentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/{deptCode}")
    public ResponseEntity<DepartmentDto> fetchDepartmentDetails(@PathVariable("deptCode") String deptCode) {
        DepartmentDto departmentDto = departmentService.fetchDepartmentByDeptCode(deptCode);
        return new ResponseEntity(departmentDto, HttpStatus.OK);
    }
}
