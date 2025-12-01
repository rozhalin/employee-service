package t1.employeeservice.dto.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeDTO {

    private String lastName;

    private String firstName;

    private String middleName;

    private String position;

    private Long departmentId;
}
