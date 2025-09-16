package com.duyphong.duyphong_app.entity;

import com.duyphong.duyphong_app.enumeration.MealType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lunch_logs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class LunchLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "employee_id", length = 255, nullable = false)
    private String employeeId;

    @Column(name = "lunch_date", nullable = false)
    private LocalDate lunchDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type", length = 50, nullable = false)
    private MealType mealType;

    @Column(name = "restaurant", length = 255)
    private String restaurant;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // Foreign key relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeEntity employee;
}
