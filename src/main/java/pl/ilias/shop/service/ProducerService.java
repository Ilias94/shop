package pl.ilias.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.ilias.shop.model.dao.Address;
import pl.ilias.shop.model.dao.Producer;
import pl.ilias.shop.repository.ProducerRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerRepository producerRepository;
    private final AddressService addressService;

    public Producer save(Producer producer, Long addressId) {
        producer.setAddress(addressService.getById(addressId));
        return producerRepository.save(producer);
    }

    public Producer getByID(Long id) {
        return producerRepository.getReferenceById(id);
    }

    public void deleteById(Long id) {
        producerRepository.deleteById(id);
    }

    public Page<Producer> getPage(Pageable pageable) {
        return producerRepository.findAll(pageable);
    }

    @Transactional
    public Producer update(Producer producer, Long id, Long addressId) {
        Producer producerDb = getByID(id);
        producerDb.setName(producer.getName());
        producerDb.setAddress(addressService.getById(addressId));
        return producerDb;
    }

    public List<Producer> getProducers() {
        return producerRepository.findAll();
    }
}
