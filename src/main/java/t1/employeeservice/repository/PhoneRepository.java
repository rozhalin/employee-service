package t1.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1.employeeservice.model.Phone;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    List<Phone> findByEmployeeId(Long employeeId);

    Phone findByNumber(String number);

    boolean existsByNumber(String number);
}
