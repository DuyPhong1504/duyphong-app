package com.duyphong.duyphong_app.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "employees")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeeEntity {

    @Id
    @Column(name = "id", length = 255, nullable = false, updatable = false)
    private String id; 

    @Column(name = "username", length = 255, nullable = false, unique = true)
    private String username;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "fullname", length = 255, nullable = false)
    private String fullname;

    @Column(name = "department", length = 255)
    private String department;

    @Column(name = "position", length = 255)
    private String position;

    @Column(name = "salary")
    private Integer salary;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
