package com.employee.api.repository;

import com.employee.api.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // 1. 이메일로 직원 찾기 (unique 제약조건 대응)
    /*Optional 사용: findByEmail이나 findByDepartmentName처럼 결과가 없거나 하나일 것이 확실한 경우
    Optional<T>을 반환하여 NullPointerException을 방어하는 것이 최신 관례*/
    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Optional<Employee> findByEmail(@Param("email") String email);

    // 2. 성(lastName)이 일치하는 모든 직원 찾기
    List<Employee> findByLastName(String lastName);

    // 3. 이름 또는 성에 특정 문자열이 포함된 직원 검색 (Like 검색)
    /*Containing 활용: SQL의 LIKE %keyword%와 동일하게 동작하여
    검색 기능을 구현할 때 매우 유용*/
    List<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    // 4. 특정 부서 ID에 속한 모든 직원 조회
    List<Employee> findByDepartmentId(Long departmentId);

    // 5. [성능 최적화] 부서 정보까지 한 번에 가져오기 (Fetch Join)
    /* N+1 문제 해결: 앞서 설명드린 FetchType.LAZY 설정 때문에 Employee 목록을 불러올 때 부서 정보가 계속 쿼리되는 현상이 생길 수 있음.
    이때, JOIN FETCH를 사용한 커스텀 쿼리를 하나 만들어두면 성능 최적화에 큰 도움 */
    @Query("SELECT e FROM Employee e JOIN FETCH e.department")
    List<Employee> findAllWithDepartment();
}