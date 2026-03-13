package com.employee.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_id", nullable = false, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    //FetchType에 LAZY가 있고 EAGER가 있음.
    //LAZY는 Employee 엔티티에서 getDepartment()호출할 때 fetch되어짐. 즉, 필요할 때만 창고에서 꺼내온다
    //EAGER Employee정보에서 무조건 한번에 다 가져옴
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
}