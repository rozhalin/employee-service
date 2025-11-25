package t1.employeeservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t1.employeeservice.dto.PhoneDTO;
import t1.employeeservice.model.Phone;
import t1.employeeservice.repository.PhoneRepository;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public Page<PhoneDTO> getAllPhones(Pageable pageable) {
        return phoneRepository.findAll(pageable)
                .map(phone -> mapper.convertValue(phone, PhoneDTO.class));
    }

    public PhoneDTO getById(Long id) {
        return mapper.convertValue(phoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phone with id " + id + " not found")),
                PhoneDTO.class);
    }

    public PhoneDTO create(PhoneDTO phoneDTO) {
        Phone phone = mapper.convertValue(phoneDTO, Phone.class);
        return mapper.convertValue(phoneRepository.save(phone), PhoneDTO.class);
    }

    public PhoneDTO update(PhoneDTO phoneDTO) {
        Phone phone = mapper.convertValue(phoneDTO, Phone.class);
        return mapper.convertValue(phoneRepository.save(phone), PhoneDTO.class);
    }

    public void delete(Long id) {
        phoneRepository.deleteById(id);
    }
}
