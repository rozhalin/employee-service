package t1.employeeservice.dto.employee;

import lombok.Getter;
import lombok.Setter;
import t1.employeeservice.dto.department.DepartmentDTO;
import t1.employeeservice.dto.phone.PhoneDTO;

import java.util.List;

@Getter
@Setter
public class EmployeeProfileDTO {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String position;

    private DepartmentDTO department;

    private List<PhoneDTO> phones;
}
