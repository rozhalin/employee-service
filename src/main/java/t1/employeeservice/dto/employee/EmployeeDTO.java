package t1.employeeservice.dto.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String position;
}
