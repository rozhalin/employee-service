package t1.employeeservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t1.employeeservice.dto.department.DepartmentDTO;
import t1.employeeservice.dto.department.UpdateDepartmentDTO;
import t1.employeeservice.repository.DepartmentRepository;
import t1.model.Department;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module());

    public Page<DepartmentDTO> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(department -> Optional.ofNullable(mapper.convertValue(department, DepartmentDTO.class)));
    }

    public Optional<DepartmentDTO> getById(Long id) {
        return Optional.ofNullable(mapper.convertValue(departmentRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found")),
                DepartmentDTO.class));
    }

    public Optional<DepartmentDTO> createDepartment(UpdateDepartmentDTO departmentDTO) {
        Department department = mapper.convertValue(departmentDTO, Department.class);
        return Optional.ofNullable(mapper.convertValue(departmentRepository.save(department), DepartmentDTO.class));
    }

    public Optional<DepartmentDTO> updateDepartment(Long id, UpdateDepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found"));
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        return Optional.ofNullable(mapper.convertValue(departmentRepository.save(department), DepartmentDTO.class));
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found"));
        departmentRepository.delete(department);
    }
}
