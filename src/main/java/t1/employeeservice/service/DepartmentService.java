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
import t1.employeeservice.model.Department;
import t1.employeeservice.repository.DepartmentRepository;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module());

    public Page<DepartmentDTO> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(department -> mapper.convertValue(department, DepartmentDTO.class));
    }

    public DepartmentDTO getById(Long id) {
        return mapper.convertValue(departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found")),
                DepartmentDTO.class);
    }

    public DepartmentDTO createDepartment(UpdateDepartmentDTO departmentDTO) {
        Department department = mapper.convertValue(departmentDTO, Department.class);
        return mapper.convertValue(departmentRepository.save(department), DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(Long id, UpdateDepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found"));
        department.setName(departmentDTO.getName());
        department.setDescription(departmentDTO.getDescription());
        return mapper.convertValue(departmentRepository.save(department), DepartmentDTO.class);
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found"));
        departmentRepository.delete(department);
    }
}
