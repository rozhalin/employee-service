package t1.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import t1.model.Phone;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    List<Optional<Phone>> findByEmployeeId(Long employeeId);

    Optional<Phone> findByNumber(String number);

    boolean existsByNumber(String number);
}
