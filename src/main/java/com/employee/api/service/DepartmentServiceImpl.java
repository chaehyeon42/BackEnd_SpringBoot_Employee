package com.employee.api.service;

import com.employee.api.dto.DepartmentDto;
import com.employee.api.entity.Department;
import com.employee.api.exception.ResourceNotFoundException;
import com.employee.api.mapper.DepartmentMapper;
import com.employee.api.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
    //의존성 추가
    // RequiredArgsConstructor -> final인 변수에 대한 생성자 만들어줌.
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        //DTO => Entity 변환
        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        //등록 처리
        Department savedDepartment = departmentRepository.save(department);
        //등록된 Entity => DTO 변환
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Transactional(readOnly = true)
    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId) //optional<Department>
                //department(Entity) -> (DTO로 변경)DepartmentMapper.mapToDepartmentDto(department)
                //Optional의 map 함수를 이용해서 바로 변경 방법 1)람다식 표현: 직접 매퍼를 호출
                // .map(department -> DepartmentMapper.mapToDepartmentDto(department))

                //Optional의 map 함수를 이용해서 바로 변경 방법 2)메소드 참조 (Method Reference)
                //body에서 하는 변환 작업을 대신 해줌
                //             클래스명::메소드명 형식을 사용
                .map(DepartmentMapper::mapToDepartmentDto) // Optional<DepartmentDto>
                //.orElseThrow 값이 없으면 에러 있으면 T인 DepartmentDto를 내뱉어줌
                .orElseThrow(() -> getNotFoundException("Department is not exists with a given id: ", departmentId));
    }

    //public <X extends Throwable> T orElseT    hrow(Supplier<? extends X> exceptionSupplier)
    //Supplier 추상메서드 T get()
    private static ResourceNotFoundException getNotFoundException(String msg,
                                                                  Long departmentId) {
        return new ResourceNotFoundException(
                msg + departmentId,
                HttpStatus.NOT_FOUND);
    }


    @Transactional(readOnly = true)
    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll(); //findAll()-> 전체 목록 가져옴
        // List<Department> 를 Stream<Department>로 변경
        return departments.stream() //Stream<Department>
                //1. 람다식
                //.map(department -> DepartmentMapper.mapToDepartmentDto(department)) //Stream<DepartmentDTO>로 변경
                //2. 클래스명::메소드명 형식
                .map(DepartmentMapper::mapToDepartmentDto) //Stream<DepartmentDTO>로 변경
                //Stream<DepartmentDTO> 를 List<DepartmentDto>로 변경
                .toList();
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        getNotFoundException("Department is not exists with a given id:", departmentId)
                );

        //setter 호출
        department.setDepartmentName(updatedDepartment.getDepartmentName());
        department.setDepartmentDescription(updatedDepartment.getDepartmentDescription());

        //Department savedDepartment = departmentRepository.save(department);

        //Entity를 DTO로 변환
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        getNotFoundException("Department is not exists with a given id:", departmentId)
                ); //ID로 조회
            //삭제처리(Entity로 삭제)
            departmentRepository.delete(department);

    }
}