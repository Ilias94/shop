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

    public Product save(Product product) { //wstrzyknac producerService (Address address, Producer producer,)
        return productRepository.save(product);
    }

    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public Product update(Product product, Long id) {
        Product productDb = getById(id);
        productDb.setName(product.getName());
        productDb.setProducer(product.getProducer());
        productDb.setPrice(product.getPrice());
        productDb.setDescription(product.getDescription());
        return productDb;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
