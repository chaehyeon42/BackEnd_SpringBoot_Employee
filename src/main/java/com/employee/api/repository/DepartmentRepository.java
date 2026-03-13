package com.employee.api.repository;

import com.employee.api.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // 1. 부서 이름으로 정확히 일치하는 부서 찾기
    /*Optional 사용: findByEmail이나 findByDepartmentName처럼 결과가 없거나 하나일 것이 확실한 경우
    Optional<T>을 반환하여 NullPointerException을 방어하는 것이 최신 관례*/
    Optional<Department> findByDepartmentName(String departmentName);

    // 2. 부서 이름에 특정 단어가 포함된 부서 목록 조회
    /*Containing 활용: SQL의 LIKE %keyword%와 동일하게 동작하여
    검색 기능을 구현할 때 매우 유용*/
    List<Department> findByDepartmentNameContaining(String keyword);

    // 3. 부서 설명이 비어있지 않은 부서들만 조회
    List<Department> findByDepartmentDescriptionIsNotNull();
}