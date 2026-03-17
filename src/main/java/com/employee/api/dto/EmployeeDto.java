package com.employee.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Builder 사용을 위해 전체 생성자 필요
//기존의 복잡한 생성자들을 모두 제거하거나 private으로 숨기고, 하나로 통합할 수 있음.
public class EmployeeDto {
    private Long id;

    @NotBlank(message = "직원 firstName은 필수 입력 항목입니다.")
    private String firstName;

    @NotBlank(message = "직원 lastName은 필수 입력 항목입니다.")
    private String lastName;

    @NotBlank(message = "직원 email은 필수 입력 항목입니다.")
    @Email
    private String email;

    @NotBlank(message = "직원 departmentId는 필수 입력 항목입니다.") //null 방지(필수)
    @Positive(message = "올바른 부서 코드가 아닙니다.") //0또는 음수방지
    private Long departmentId;

    private DepartmentDto departmentDto;

}