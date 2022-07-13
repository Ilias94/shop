package pl.ilias.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ilias.shop.model.dao.Producer;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
