package t1.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1.employeeservice.model.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
