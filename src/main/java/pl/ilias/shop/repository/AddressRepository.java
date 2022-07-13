package pl.ilias.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ilias.shop.model.dao.Address;

public interface AddressRepository extends JpaRepository<Address, Long>  {
}
