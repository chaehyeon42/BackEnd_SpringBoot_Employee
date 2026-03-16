package com.employee.api.service.impl;

import com.employee.api.dto.EmployeeDto;
import com.employee.api.entity.Department;
import com.employee.api.entity.Employee;
import com.employee.api.exception.ResourceNotFoundException;
import com.employee.api.mapper.EmployeeMapper;
import com.employee.api.repository.DepartmentRepository;
import com.employee.api.repository.EmployeeRepository;
import com.employee.api.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.employee.api.service.common.CommonService.getNotFoundExceptionSupplier;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        //입력 받은 DTO를 Entity로 변경
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        //Department(부서정보) 존재여부 를 DepartmentId로 조회
        Department department = departmentRepository.findById(employeeDto.getDepartmentId())
                .orElseThrow(getNotFoundExceptionSupplier(
                        "Department is not exists with id: ",
                        employeeDto.getDepartmentId())
                );

        //Employee와 Department 연결
        employee.setDepartment(department);
        //Employee 등록
        Employee savedEmployee = employeeRepository.save(employee);
        //DB에 등록된 Entity를 DTO로 변경
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);

    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(
                        getNotFoundExceptionSupplier(
                                "Employee is not exists with given id : ",
                                employeeId))
                ;

        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
        //.map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
        //.collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> getAllEmployeesDepartment() {
        List<Employee> employees = employeeRepository.findAllWithDepartment();
        return employees.stream()
                .map(EmployeeMapper::mapToEmployeeDto)
                .toList();
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        //해당 ID로 Employee 조회
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(
                        getNotFoundExceptionSupplier(
                                "Employee is not exists with given id : ",
                                employeeId)
                );

        //Setter 호출로 값을 변경
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        //updateEmployee에 들어있는 부서 ID 로 업데이트
        Department department = departmentRepository.findById(updatedEmployee.getDepartmentId())
                .orElseThrow(getNotFoundExceptionSupplier(
                        "Department is not exists with a given id: ", updatedEmployee.getDepartmentId())
                );
        //Employee와 Department를 연결
        employee.setDepartment(department);
                                    //employee를 DB에 반영
        Employee updatedEmployeeObj = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {

    }
}
