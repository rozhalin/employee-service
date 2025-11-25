package t1.employeeservice.dto;

import lombok.*;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeDTO {
    //todo подумать над полями и вообще над этим дто, нужен ли он
    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String position;

    private Long departmentId;

    private HashSet<PhoneDTO> phones;
}
