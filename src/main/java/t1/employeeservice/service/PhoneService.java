package t1.employeeservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t1.employeeservice.dto.phone.AddPhoneDTO;
import t1.employeeservice.dto.phone.PhoneDTO;
import t1.employeeservice.model.Phone;
import t1.employeeservice.repository.PhoneRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new Jdk8Module());

    public Page<PhoneDTO> getAllPhones(Pageable pageable) {
        return phoneRepository.findAll(pageable)
                .map(phone -> mapper.convertValue(phone, PhoneDTO.class));
    }

    public PhoneDTO getById(Long id) {
        return mapper.convertValue(phoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phone with id " + id + " not found")),
                PhoneDTO.class);
    }

    public PhoneDTO create(AddPhoneDTO addPhoneDTO) {
        Phone phone = mapper.convertValue(addPhoneDTO, Phone.class);
        return mapper.convertValue(phoneRepository.save(phone), PhoneDTO.class);
    }

    public PhoneDTO update(PhoneDTO phoneDTO) {
        Phone phone = mapper.convertValue(phoneDTO, Phone.class);
        return mapper.convertValue(phoneRepository.save(phone), PhoneDTO.class);
    }

    public void delete(Long id) {
        phoneRepository.deleteById(id);
    }

    public boolean existsByNumber(String phoneNumber) {
        return phoneRepository.existsByNumber(phoneNumber);
    }
}
