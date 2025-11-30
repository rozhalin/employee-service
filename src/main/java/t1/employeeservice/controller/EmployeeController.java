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
import t1.employeeservice.dto.employee.CreateEmployeeDTO;
import t1.employeeservice.dto.employee.EmployeeDTO;
import t1.employeeservice.dto.employee.EmployeeProfileDTO;
import t1.employeeservice.dto.employee.UpdateEmployeeDTO;
import t1.employeeservice.dto.phone.AddPhoneDTO;
import t1.employeeservice.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Список всех сотрудников",
            description = "Получить список всех сотрудников с сортировкой и пагинацией")
    public ResponseEntity<Page<EmployeeDTO>> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployees(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить инфо о сотруднике",
            description = "Получить информацию о сотруднике по ID")
    public ResponseEntity<EmployeeDTO> getEmployee(@Parameter(description = "ID сотрудника") @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @GetMapping("/search/by-lastname")
    @Operation(summary = "Поиск по фамилии", description = "Найти сотрудников по фамилии")
    public ResponseEntity<Page<EmployeeDTO>> findEmployeesByLastName(
            @Parameter(description = "Фамилия для поиска") @RequestParam String lastname,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(employeeService.findByLastName(lastname, pageable));
    }

    @PostMapping()
    @Operation(summary = "Создать сотрудника", description = "Создать новую карточку сотрудника")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody CreateEmployeeDTO employeeDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employeeDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить сотрудника", description = "Обновить информацию о сотруднике")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @Parameter(description = "ID сотрудника") @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeDTO updateEmployeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, updateEmployeeDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить сотрудника", description = "Полностью удалить сотрудника по ID")
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "ID сотрудника") @PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile/{id}")
    @Operation(summary = "Получить карточку пользователя",
            description = "Получить карточку пользователя с полной информацией")
    public ResponseEntity<EmployeeProfileDTO> getEmployeeProfileDTO(
            @Parameter(description = "ID сотрудника") @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeProfile(id));
    }

    @PostMapping("/phones/{id}")
    @Operation(summary = "Добавить телефон",
            description = "Добавить телефон пользователю по ID")
    public ResponseEntity<EmployeeProfileDTO> addPhoneToEmployee(
            @Parameter(description = "ID сотрудника") @PathVariable Long id,
            @Valid @RequestBody AddPhoneDTO addPhoneDTO
    ) {
        return ResponseEntity.ok(employeeService.addPhoneToEmployee(id, addPhoneDTO));
    }

    @DeleteMapping("/{employeeId}/phones/{phoneId}")
    @Operation(summary = "Удалить телефон",
        description = "Удалить телефон у сотрудника по ID")
    public ResponseEntity<Void> deletePhoneFromEmployee(
            @Parameter(description = "ID сотрудника") @PathVariable Long employeeId,
            @Parameter(description = "ID телефона") @PathVariable Long phoneId
            ) {
        employeeService.removePhoneFromEmployee(employeeId, phoneId);
        return ResponseEntity.noContent().build();
    }
}
