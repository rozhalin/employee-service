package t1.employeeservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1.employeeservice.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //Page<Employee> findAll(Pageable pageable, Sort sort);

    //Employee findOneById(Long id);

    Page<Employee> findEmployeeByLastNameStartingWithIgnoreCase
            (String lastNamePart, Pageable pageable, Sort sort);

    Page<Employee> findEmployeeByDepartment(String department, Pageable pageable, Sort sort);

    Page<Employee> findEmployeeByPhoneContaining(String phone, Pageable pageable, Sort sort);
}
