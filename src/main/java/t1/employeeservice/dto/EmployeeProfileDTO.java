package t1.employeeservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
public class EmployeeProfileDTO {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String position;

    private DepartmentDTO department;

    private HashSet<PhoneDTO> phones;
}
