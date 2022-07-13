package pl.ilias.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.ilias.shop.model.dao.Address;
import pl.ilias.shop.model.dao.Producer;
import pl.ilias.shop.model.dao.Product;
import pl.ilias.shop.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProducerService producerService;

    public Product save(Product product, Long producerId) { //wstrzyknac producerService (Address address, Producer producer,)
        product.setProducer(producerService.getByID(producerId));
        return productRepository.save(product);
    }

    public Product getById(Long id) {
        return productRepository.getReferenceById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public Product update(Product product, Long id, Long producerId) {
        Product productDb = getById(id);
        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());
        productDb.setDescription(product.getDescription());
        productDb.setProducer(producerService.getByID(producerId));
        return productDb;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
