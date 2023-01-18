package pl.ilias.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.ilias.shop.fixtures.MySqlContainerFixtures;
import pl.ilias.shop.model.dao.User;
import pl.ilias.shop.model.dto.UserDto;
import pl.ilias.shop.repository.UserRepository;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest extends MySqlContainerFixtures {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserDto.builder()
                                .email("emailTest@gmail.com")
                                .firstName("Adam")
                                .lastName("Kowalski")
                                .password("kowal123")
                                .confirmPassword("kowal123")
                                .build())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Adam"))
                .andExpect(jsonPath("$.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.email").value("emailTest@gmail.com"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.confirmPassword").doesNotExist())
                .andExpect(jsonPath("$.revisionNumber").doesNotExist())
                .andExpect(jsonPath("$.revisionType").doesNotExist());
    }

    @Test
    void shouldNotSaveUserWithWrongData() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserDto.builder().build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[*].field", containsInAnyOrder("firstName", "lastName",
                        "email")))
                .andExpect(jsonPath("$.[*].message", containsInAnyOrder("must not be blank", "must not be blank", "must not be blank")));
    }

    @Test
    void shouldNotSaveUserWithWrongPassword() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserDto.builder()
                                .email("emailTest@gmail.com")
                                .firstName("Adam")
                                .lastName("Kowalski")
                                .password("kowal123")
                                .confirmPassword("kowal12")
                                .build())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[*].field", containsInAnyOrder("firstName", "lastName",
                        "email")))
                .andExpect(jsonPath("$.[*].message", containsInAnyOrder("Unmatched password")));
    }

    @Test
    @WithMockUser(authorities = "SCOPE_ADMIN")
    void shouldGetUserPage() throws Exception {
        userRepository.save(User.builder()
                .email("user1@gmail.com")
                .firstName("Adam")
                .lastName("Kowalski")
                .password("kowal123")
                .build());
        userRepository.save(User.builder()
                .email("user2@gmail.com")
                .firstName("Jan")
                .lastName("Błachowicz")
                .password("jan123")
                .build());

        mockMvc.perform(get("/api/v1/users")
                        .queryParam("page", "0")
                        .queryParam("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void ShouldNotGetUserPage() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .queryParam("page", "0")
                        .queryParam("size", "2"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
