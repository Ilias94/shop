package pl.ilias.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pl.ilias.shop.mapper.HistoryMapper;
import pl.ilias.shop.model.dto.ProductDto;
import pl.ilias.shop.model.dto.UserDto;
import pl.ilias.shop.repository.ProductRepository;
import pl.ilias.shop.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/histories")
@RequiredArgsConstructor
public class HistoryController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HistoryMapper historyMapper;

    @GetMapping("users/{id}")
    Page<UserDto> getUserHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return userRepository.findRevisions(id, PageRequest.of(page, size)).map(historyMapper::revisionToUserDto);
    }

    @GetMapping("products/{id}")
    Page<ProductDto> getProductHistory(@PathVariable Long id, @RequestParam int page, @RequestParam int size) {
        return productRepository.findRevisions(id, PageRequest.of(page, size)).map(historyMapper::revisionToProductDto);
    }
}
