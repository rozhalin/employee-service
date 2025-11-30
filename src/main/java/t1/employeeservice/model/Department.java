package t1.employeeservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "departments",
        indexes = { @Index(name = "idx_department_name", columnList = "name") }
)
@Getter
@Setter
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;
}
