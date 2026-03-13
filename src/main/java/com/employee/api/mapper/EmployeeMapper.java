package com.employee.api.mapper;

import com.employee.api.dto.EmployeeDto;
import com.employee.api.entity.Department;
import com.employee.api.entity.Employee;

public class EmployeeMapper {

    /**
     * [Entity -> DTO 변환] (경량 버전)
     * 직원의 기본 정보와 '부서 ID'만 담아서 보낼 때 사용합니다.
     * 주로 리스트 조회처럼 부서의 상세 내용까지는 필요 없을 때 효율적입니다.
     */
    public static EmployeeDto mapToEmployeeDto(Employee employee){
        return EmployeeDto.builder() // 빌더 패턴 시작
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                // 연관된 부서 객체에서 ID값만 추출 (이때 지연 로딩 최적화가 가능할 수 있음)
                .departmentId(employee.getDepartment().getId())
                .build(); // 최종 객체 생성
    }

    /**
     * [Entity -> DTO 변환] (상세 버전)
     * 직원의 정보와 함께 '부서 전체 정보(DTO)'를 중첩해서 담습니다.
     * 직원 상세 페이지처럼 부서명, 부서 위치 등이 모두 필요할 때 사용합니다.
     */
    public static EmployeeDto mapToEmployeeDepartmentDto(Employee employee){
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                // DepartmentMapper를 재사용하여 부서 엔티티를 부서 DTO로 완전히 변환해서 담음
                .departmentDto(DepartmentMapper.mapToDepartmentDto(employee.getDepartment()))
                .build();
    }

    /**
     * [DTO -> Entity 변환]
     * 클라이언트로부터 받은 입력 데이터(DTO)를 DB에 저장하거나 수정하기 위해
     * 실제 DB와 연결된 엔티티 객체로 변환합니다.
     */
    public static Employee mapToEmployee(EmployeeDto employeeDto){
        // 주의: 엔티티에도 @Builder가 선언되어 있다면 아래의 new와 Setter 방식 대신
        // Employee.builder()...build() 방식을 사용하는 것이 객체의 불변성 유지에 더 좋습니다.
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());

        // Tip: 만약 수정(Update) 로직이라면 여기서 부서(Department) 정보를
        // 어떻게 찾아와서(Repository.findById) 꽂아줄지에 대한 고민이 추가로 필요합니다.
        return employee;
    }

}