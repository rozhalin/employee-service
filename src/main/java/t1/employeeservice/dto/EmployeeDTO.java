package t1.employeeservice.dto;

import lombok.*;

import java.util.HashSet;

@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String position;

    private DepartmentDTO department;

    private HashSet<PhoneDTO> phones;
}
