package pl.ilias.shop.service

import org.springframework.web.multipart.MultipartFile
import pl.ilias.shop.config.properties.FolderPropertiesConfig
import pl.ilias.shop.helper.FileHelper
import pl.ilias.shop.model.dao.Producer
import pl.ilias.shop.model.dao.Product
import pl.ilias.shop.repository.ProductRepository
import spock.lang.Specification

import java.nio.file.Paths

class ProductServiceSpec extends Specification {
    def productRepository = Mock(ProductRepository)
    def producerService = Mock(ProducerService)
    def folderPropertiesConfig = Mock(FolderPropertiesConfig)
    def fileHelper = Mock(FileHelper)
    def productService = new ProductService(productRepository, producerService, folderPropertiesConfig, fileHelper)

    def ' should save product'() {
        given:
        def product = Mock(Product)
        def multipartFile = Mock(MultipartFile)
        def producerId = 5
        def producer = Mock(Producer)
        def path = Paths.get("pictures", "8.img")
        def inputStream = Mock(InputStream)

        when:
        productService.save(product, producerId, multipartFile)

        then:
        1 * producerService.getByID(producerId) >> producer
        1 * product.setProducer(producer)
        1 * productRepository.save(product)
        1 * folderPropertiesConfig.getProduct() >> "pictures"
        1 * product.getId() >> 8
        1 * multipartFile.getOriginalFilename() >> "laptop.img"
        1 * multipartFile.getInputStream() >> inputStream
        1 * fileHelper.save(inputStream, path)
        1 * product.setImagePath(path.toString())
        0 * _
    }
}
