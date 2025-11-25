package t1.employeeservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1.employeeservice.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Page<Department> findDepartmentByName(String name, Pageable pageable, Sort sort);
}
