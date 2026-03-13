package com.employee.api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Builder 사용을 위해 전체 생성자 필요
//기존의 복잡한 생성자들을 모두 제거하거나 private으로 숨기고, 하나로 통합할 수 있음.
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;

    private DepartmentDto departmentDto;

}