package t1.employeeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
        name = "employees",
        indexes = { @Index(name = "idx_employee_last_name", columnList = "last_name") }
)
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastName;

    @Column(nullable = false)
    @NotBlank(message = "Имя не может быть пустым")
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Phone> phones;

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setEmployee(this);
    }

    public void removePhone(Phone phone) {
        phones.remove(phone);
        phone.setEmployee(null);
    }
}
