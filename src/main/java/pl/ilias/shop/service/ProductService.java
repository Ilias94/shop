package pl.ilias.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.ilias.shop.config.properties.FolderPropertiesConfig;
import pl.ilias.shop.helper.FileHelper;
import pl.ilias.shop.model.dao.Product;
import pl.ilias.shop.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProducerService producerService;
    private final FolderPropertiesConfig folderPropertiesConfig;
    private final FileHelper fileHelper;

    @Transactional
    @CachePut(cacheNames = "product", key = "#result.id")
    public Product save(Product product, Long producerId, MultipartFile image) {
        product.setProducer(producerService.getByID(producerId));
        productRepository.save(product);
        Path path = Paths.get(folderPropertiesConfig.getProduct(), product.getId() + "." +
                FilenameUtils.getExtension(image.getOriginalFilename()));//todo
        try {
            fileHelper.save(image.getInputStream(), path);
            product.setImagePath(path.toString());
        } catch (IOException e) {
            log.warn("Couldn't save file", e);
        }
        return product;
    }

    @Cacheable(cacheNames = "product", key = "#id")
    public Product getById(Long id) {
        return productRepository.getReferenceById(id);
    }

    @CacheEvict(cacheNames = "product", key = "#id")
    public void deleteById(Long id) {
        productRepository.deleteById(id);//todo
    }

    public Page<Product> getPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    @CachePut(cacheNames = "product", key = "#result.id")
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
