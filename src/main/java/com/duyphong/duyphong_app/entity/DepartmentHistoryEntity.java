package com.duyphong.duyphong_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "department_history")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class DepartmentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "employee_id", length = 255, nullable = false)
    private String employeeId;

    @Column(name = "old_department_id", length = 255)
    private String oldDepartmentId;

    @Column(name = "new_department_id", length = 255, nullable = false)
    private String newDepartmentId;

    @Column(name = "change_date", nullable = false)
    private Instant changeDate;

    // Foreign key relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private DepartmentEntity oldDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private DepartmentEntity newDepartment;
}
