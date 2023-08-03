package com.example.employeeservice.service;

import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.dto.OrganizationDto;
import com.example.employeeservice.model.Employee;
import com.example.employeeservice.respository.EmployeeRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
    private static final Logger LOGGER= LoggerFactory.getLogger(EmployeeServiceImpl.class);

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
                .organizationCode(employeeDto.getOrganizationCode())
                .build();
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDto1 = EmployeeDto.builder()
                .empId(savedEmployee.getEmpId())
                .firstName(savedEmployee.
                        getFirstName())
                .lastName(savedEmployee.getLastName())
                .email(savedEmployee.getEmail())
                .deptCode(savedEmployee.getDeptCode())
                .organizationCode(savedEmployee.getOrganizationCode())
                .build();
        return employeeDto1;
    }

  //  @CircuitBreaker(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Override
    public ApiResponseDto getEmployeeById(Long empId) {
        LOGGER.info("Inside getEmployeeById");
        Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("empId " + empId + " not exists"));
//        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/dept/" + employee.getDeptCode(),
//                DepartmentDto.class);
        //  DepartmentDto departmentDto = responseEntity.getBody();
        String uri="http://localhost:8080/api/dept/" + employee.getDeptCode();

        DepartmentDto departmentDto = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

//        DepartmentDto departmentDto = apiClient.fetchDepartmentDetails(employee.getDeptCode());
        String uriOrg="http://localhost:8083/api/org/" + employee.getOrganizationCode();
        OrganizationDto organizationDto = webClient.get()
                .uri(uriOrg)
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();

        EmployeeDto employeeDto = EmployeeDto.builder()
                .empId(employee.getEmpId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .deptCode(employee.getDeptCode())
                .organizationCode(employee.getOrganizationCode())
                .build();
        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .departmentDto(departmentDto)
                .employeeDto(employeeDto)
                .organizationDto(organizationDto)
                .build();
        return apiResponseDto;
    }

    public ApiResponseDto getDefaultDepartment(Long empId,Exception exception){
        LOGGER.info("Inside fallBack getDefaultDepartment");
        Employee employee = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("empId " + empId + " not exists"));
        EmployeeDto employeeDto = EmployeeDto.builder()
                .empId(employee.getEmpId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .deptCode(employee.getDeptCode())
                .organizationCode(employee.getOrganizationCode())
                .build();

        DepartmentDto departmentDto = DepartmentDto.builder()
                .deptCode("RND001")
                .deptName("RnD")
                .deptDesc("Research and Development")
                .build();

        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .departmentDto(departmentDto)
                .employeeDto(employeeDto)
                .build();
        return apiResponseDto;
    }
}
