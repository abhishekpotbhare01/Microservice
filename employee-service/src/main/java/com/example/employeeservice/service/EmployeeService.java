package com.example.employeeservice.service;

import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.model.Employee;
import com.example.employeeservice.respository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService {

    private EmployeeRepository employeeRepository;
    private RestTemplate restTemplate;
    private WebClient webClient;
    private ApiClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .deptCode(employeeDto.getDeptCode())
                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .empId(savedEmployee.getEmpId())
                .firstName(savedEmployee.
                        getFirstName())
                .lastName(savedEmployee.getLastName())
                .email(savedEmployee.getEmail())
                .deptCode(savedEmployee.getDeptCode())
                .build();
        return employeeDto1;
    }

    @Override
    public ApiResponseDto getEmployeeById(Long empId) {
        Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("empId " + empId + " not exists"));
//        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/dept/" + employee.getDeptCode(),
//                DepartmentDto.class);
        //  DepartmentDto departmentDto = responseEntity.getBody();
//        String uri="http://localhost:8080/api/dept/" + employee.getDeptCode();
//
//        DepartmentDto departmentDto = webClient.get()
//                .uri(uri)
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();


        DepartmentDto departmentDto = apiClient.fetchDepartmentDetails(employee.getDeptCode());

        EmployeeDto employeeDto = EmployeeDto.builder()
                .empId(employee.getEmpId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .deptCode(employee.getDeptCode())
                .build();
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .departmentDto(departmentDto)
                .employeeDto(employeeDto)
                .build();
        return apiResponseDto;
    }

}
