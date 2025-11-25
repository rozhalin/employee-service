package t1.employeeservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t1.employeeservice.dto.DepartmentDTO;
import t1.employeeservice.model.Department;
import t1.employeeservice.repository.DepartmentRepository;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public Page<DepartmentDTO> getAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable)
                .map(department -> mapper.convertValue(department, DepartmentDTO.class));
    }

    public DepartmentDTO getById(Long id) {
        return mapper.convertValue(departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found")),
                DepartmentDTO.class);
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = mapper.convertValue(departmentDTO, Department.class);
        return mapper.convertValue(departmentRepository.save(department), DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = mapper.convertValue(departmentDTO, Department.class);
        return mapper.convertValue(departmentRepository.save(department), DepartmentDTO.class);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
