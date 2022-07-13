package pl.ilias.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ilias.shop.model.dao.Address;
import pl.ilias.shop.repository.AddressRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address getById(Long id) {
        return addressRepository.getReferenceById(id);
    }

    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

    public Page<Address> getPage(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    @Transactional
    public Address update(Address address, Long id) {
        Address addressDb = getById(id);
        addressDb.setCity(address.getCity());
        addressDb.setStreet(address.getStreet());
        addressDb.setZipCode(address.getZipCode());
        return addressDb;
    }

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }
}
