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
import t1.employeeservice.dto.employee.EmployeeDTO;
import t1.employeeservice.dto.employee.EmployeeProfileDTO;
import t1.employeeservice.dto.employee.UpdateEmployeeDTO;
import t1.employeeservice.dto.phone.AddPhoneDTO;
import t1.employeeservice.model.Department;
import t1.employeeservice.model.Employee;
import t1.employeeservice.model.Phone;
import t1.employeeservice.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module());
    private final PhoneService phoneService;

    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employee -> mapper.convertValue(employee, EmployeeDTO.class));
    }

    public EmployeeDTO getById (Long id) {
        return mapper.convertValue(employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + id + " not found")),
                EmployeeDTO.class);
    }

    public EmployeeDTO createEmployee(CreateEmployeeDTO employeeDTO) {
        Employee employee = mapper.convertValue(employeeDTO, Employee.class);
        return mapper.convertValue(employeeRepository.save(employee), EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long employeeId, UpdateEmployeeDTO updateEmployeeDTO) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " not found"));
        employee.setFirstName(updateEmployeeDTO.getFirstName());
        employee.setLastName(updateEmployeeDTO.getLastName());
        employee.setPosition(updateEmployeeDTO.getPosition());

        Department department = mapper.convertValue(
                departmentService.getById(updateEmployeeDTO.getDepartmentId()),
                Department.class);
        employee.setDepartment(department);

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

    public EmployeeProfileDTO getEmployeeProfile(Long employeeId) {
        return mapper.convertValue(employeeRepository.findById(employeeId), EmployeeProfileDTO.class);
    }

    @Transactional
    public EmployeeProfileDTO addPhoneToEmployee(Long employeeId, AddPhoneDTO addPhoneDTO) {
        if (phoneService.existsByNumber(addPhoneDTO.getNumber())) {
            throw new IllegalArgumentException("Phone number already exists: " + addPhoneDTO.getNumber());
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Phone phone = mapper.convertValue(addPhoneDTO, Phone.class);

        employee.addPhone(phone);

        return mapper.convertValue(employeeRepository.save(employee), EmployeeProfileDTO.class);
    }

    @Transactional
    public EmployeeProfileDTO removePhoneFromEmployee(Long employeeId, Long phoneId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Phone phone = employee.getPhones().stream()
                .filter(p -> p.getId().equals(phoneId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Phone not found"));

        employee.removePhone(phone);

        return mapper.convertValue(employeeRepository.save(employee), EmployeeProfileDTO.class);
    }
}
