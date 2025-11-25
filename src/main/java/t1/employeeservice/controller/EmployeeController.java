package t1.employeeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import t1.employeeservice.dto.EmployeeDTO;
import t1.employeeservice.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @Operation(summary = "Список всех сотрудников",
            description = "Получить список всех сотрудников с сортировкой и пагинацией")
    public ResponseEntity<Page<EmployeeDTO>> getAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<EmployeeDTO> employees = employeeService.getAllEmployees(pageable);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить карточку сотрудника",
            description = "Получить полную информацию о сотруднике по ID")
    public ResponseEntity<EmployeeDTO> getEmployee(
            @Parameter(description = "ID сотрудника")
            @PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeService.getById(id);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/search/by-lastname")
    @Operation(summary = "Поиск по фамилии", description = "Найти сотрудников по фамилии")
    public ResponseEntity<Page<EmployeeDTO>> findEmployeesByLastName(
            @Parameter(description = "Фамилия для поиска") @RequestParam String lastname,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<EmployeeDTO> employeeDTOS = employeeService.findByLastName(lastname, pageable);
        return ResponseEntity.ok(employeeDTOS);
    }
    //todo
}
