package pl.ilias.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.ilias.shop.fixtures.MySqlContainerFixtures;
import pl.ilias.shop.model.dao.Producer;
import pl.ilias.shop.model.dto.ProductDto;
import pl.ilias.shop.repository.ProducerRepository;
import pl.ilias.shop.repository.ProductRepository;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest extends MySqlContainerFixtures {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProducerRepository producerRepository;

    @Test
    @WithMockUser(authorities = "SCOPE_ADMIN")
    public void shouldSaveProduct() throws Exception {
        Producer producer = producerRepository.save(Producer.builder()
                .name("producer1")
                .build());
        MockMultipartFile product = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(ProductDto.builder()
                .name("product1")
                .description("description")
                .price(BigDecimal.TEN)
                .quantity(10)
                .producerId(producer.getId())
                .build()));

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]);
        mockMvc.perform(multipart("/api/v1/products")
                        .file(product)
                        .file(image)
                        .with(processor -> {
                            processor.setMethod("POST");
                            return processor;
                        }))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.imagePath").exists())
                .andExpect(jsonPath("$.name").value("product1"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.price").value(BigDecimal.TEN))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.producerId").value(producer.getId()));
    }

    @Test
    @WithMockUser
    public void shouldNotSaveProductWithoutAdminRole() throws Exception {
        Producer producer = producerRepository.save(Producer.builder()
                .name("producer1")
                .build());
        MockMultipartFile product = new MockMultipartFile("product", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(ProductDto.builder()
                .name("product1")
                .description("description")
                .price(BigDecimal.TEN)
                .quantity(10)
                .producerId(producer.getId())
                .build()));
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]);
        mockMvc.perform(multipart("/api/v1/products")
                        .file(product)
                        .file(image)
                        .with(processor -> {
                            processor.setMethod("POST");
                            return processor;
                        }))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$").doesNotExist());
    }
    //todo pododwac kolejne testy
}
