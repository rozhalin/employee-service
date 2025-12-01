package t1.employeeservice.dto.phone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneDTO {

    private Long id;

    private String number;

    private String type;

    private Long employeeId;
}
