package t1.employeeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import t1.employeeservice.dto.DepartmentDTO;
import t1.employeeservice.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @Operation(summary = "Список всех подразделений",
            description = "Получить список всех подразделений с сортировкой и пагинацией")
    public ResponseEntity<Page<DepartmentDTO>> getAllDepartments(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(departmentService.getAllDepartments(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить подразделение",
            description = "Получить полную информацию о подразделении")
    public ResponseEntity<DepartmentDTO> getDepartment(
            @Parameter(description = "ID подразделения")
            @PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @PostMapping()
    @Operation(summary = "Создать подразделение", description = "Создать новое подразделение")
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(departmentDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить подразделение", description = "Обновить информацию о подразделении")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @Parameter(description = "ID подразделения") @PathVariable Long id,
            @Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить подразделение", description = "Полностью удалить подразделение по ID")
    public ResponseEntity<Void> deleteDepartment(
            @Parameter(description = "ID подразделения") @PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
