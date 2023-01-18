package pl.ilias.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.ilias.shop.mapper.ProductMapper;
import pl.ilias.shop.model.dto.ProductDto;
import pl.ilias.shop.service.ProductService;
import pl.ilias.shop.validator.ImageValid;
import pl.ilias.shop.validator.group.Create;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public Page<ProductDto> getProducts(@RequestParam int page, @RequestParam int size) {
        return productService.getPage(PageRequest.of(page, size)).map(productMapper::productToDto);
    }

    @GetMapping("{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productMapper.productToDto(productService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ProductDto saveProduct(@RequestPart @Valid ProductDto product,
                                  @RequestPart(required = false) @Valid @ImageValid MultipartFile image) {
        return productMapper.productToDto(productService.save(productMapper.productDtoToProduct(product), product.getProducerId(), image));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ProductDto updateProduct(@RequestBody ProductDto product, @PathVariable Long id) {
        return productMapper.productToDto(productService.update(productMapper.productDtoToProduct(product), id, product.getProducerId()));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
