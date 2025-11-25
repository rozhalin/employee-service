package t1.employeeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t1.employeeservice.dto.EmployeeDTO;
import t1.employeeservice.dto.UpdateEmployeeDTO;
import t1.employeeservice.model.Department;
import t1.employeeservice.model.Employee;
import t1.employeeservice.model.Phone;
import t1.employeeservice.repository.DepartmentRepository;
import t1.employeeservice.repository.EmployeeRepository;
import t1.employeeservice.repository.PhoneRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PhoneRepository phoneRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employee -> mapper.convertValue(employee, EmployeeDTO.class));
    }

    public EmployeeDTO getById (Long id) {
        return mapper.convertValue(employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + id + " not found")),
                EmployeeDTO.class);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapper.convertValue(employeeDTO, Employee.class);
        return mapper.convertValue(employeeRepository.save(employee), EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long employeeId, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " not found"));
        employee.setFirstName(updateEmployeeDTO.getFirstName());
        employee.setLastName(updateEmployeeDTO.getLastName());
        employee.setPosition(updateEmployeeDTO.getPosition());

        Department department = departmentRepository.findById(updateEmployeeDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department with id " +
                        updateEmployeeDTO.getDepartmentId() + " not found"));
        employee.setDepartment(department);

        HashSet<Phone> newPhones = new HashSet<>();
        for (Long id : updateEmployeeDTO.getPhoneIds()) {
            Phone phone = phoneRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Phone with id " + id + " not found"));
            newPhones.add(phone);
        }
        employee.setPhones(newPhones);

        return mapper.convertValue(employeeRepository.save(employee), EmployeeDTO.class);
    }

    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " not found"));
        employeeRepository.delete(employee);
    }

    public Page<EmployeeDTO> findByLastName(String lastName, Pageable pageable) {
        return employeeRepository.findEmployeeByLastNameStartingWithIgnoreCase(lastName, pageable)
                .map(employee -> mapper.convertValue(employee, EmployeeDTO.class));
    }
}
