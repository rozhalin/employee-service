package t1.employeeservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import t1.employeeservice.dto.employee.CreateEmployeeDTO;
import t1.employeeservice.dto.employee.UpdateEmployeeDTO;
import t1.employeeservice.repository.EmployeeRepository;
import t1.model.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final PhoneService phoneService;

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module());

    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Optional<Employee> findEmployeeById (Long id) {
        return Optional.ofNullable(
                employeeRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Employee with id " + id + " not found")));
    }

    @Transactional
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = mapper.convertValue(employeeRequest, Employee.class);
        Department department = mapper.convertValue(
                departmentService.getById(employeeRequest.getDepartmentId()),
                Department.class);

        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    public Optional<Employee> updateEmployee(Long employeeId, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " not found"));
        if (updateEmployeeDTO.getFirstName() != null) employee.setFirstName(updateEmployeeDTO.getFirstName());
        if (updateEmployeeDTO.getLastName() != null) employee.setLastName(updateEmployeeDTO.getLastName());
        if (updateEmployeeDTO.getPosition() != null) employee.setPosition(updateEmployeeDTO.getPosition());
        if (updateEmployeeDTO.getMiddleName() != null) employee.setMiddleName(updateEmployeeDTO.getMiddleName());

        if (updateEmployeeDTO.getDepartmentId() != null) {
            Department department = mapper.convertValue(
                    departmentService.getById(updateEmployeeDTO.getDepartmentId()),
                    Department.class);
            employee.setDepartment(department);
        }

        return Optional.of(employeeRepository.save(employee));
    }

    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " not found"));
        employeeRepository.delete(employee);
    }

    public Page<Employee> findByLastName(String lastName, Pageable pageable) {
        return employeeRepository.findEmployeeByLastNameStartingWithIgnoreCase(lastName, pageable);
    }
}
