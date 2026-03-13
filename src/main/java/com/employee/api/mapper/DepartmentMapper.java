package com.employee.api.mapper;

import com.employee.api.dto.DepartmentDto;
import com.employee.api.entity.Department;

public class DepartmentMapper {
    // Entity -> DTO  : 서버에서 응답을 줄때 Entity를 DTO로 변환
    public static DepartmentDto mapToDepartmentDto(Department department){
        return DepartmentDto.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .departmentDescription(department.getDepartmentDescription())
                .build();
    }

    // DTO -> Entity : 클라이언트에서 REPOSITORY에 SAVE 할때
    public static Department mapToDepartment(DepartmentDto departmentDto){
        // Department 엔티티에도 @Builder가 있다면 아래와 같이 작성 가능합니다.
        return new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription()
        );
        /* 만약 Department 엔티티에 @Builder를 추가했다면:
        return Department.builder()
                .id(departmentDto.getId())
                .departmentName(departmentDto.getDepartmentName())
                .departmentDescription(departmentDto.getDepartmentDescription())
                .build();
        */
    }
}