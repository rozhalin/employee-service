package t1.employeeservice.dto;

import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String position;

    private String department;

    private Long departmentId;

    private HashSet<PhoneDTO> phones;
}
