package t1.employeeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t1.employeeservice.dto.EmployeeDTO;
import t1.employeeservice.model.Department;
import t1.employeeservice.model.Employee;
import t1.employeeservice.repository.EmployeeRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;
    private final ObjectMapper mapper = new ObjectMapper();

    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return repository.findAll(pageable)
                .map(employee -> mapper.convertValue(employee, EmployeeDTO.class));
    }

    public EmployeeDTO getEmployeeById(Long employeeId) {
        return mapper.convertValue(repository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " not found")),
                EmployeeDTO.class);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapper.convertValue(employeeDTO, Employee.class);
        return mapper.convertValue(repository.save(employee), EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " not found"));
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setPosition(employeeDTO.getPosition());
        //todo Обновить department, phones
        return mapper.convertValue(repository.save(employee), EmployeeDTO.class);
    }
}
