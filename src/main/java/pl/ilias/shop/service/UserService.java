package pl.ilias.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ilias.shop.model.dao.User;
import pl.ilias.shop.repository.RoleRepository;
import pl.ilias.shop.repository.UserRepository;
import pl.ilias.shop.security.SecurityUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public User save(User user) {
        roleRepository.findByName("USER").ifPresent(role -> user.setRoleList(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", user.getFirstName());
        variables.put("lastName", user.getLastName());
        mailService.sendEmail(user.getEmail(), "Welcome email", variables, null, null);
        return user;
    }

    public User getById(Long id) {
        return userRepository.getReferenceById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    public User update(User user, Long id) {
        User userDb = getById(id);
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setEmail(user.getEmail());
        return userDb;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User currentLoginUser() {
        String userEmail = SecurityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(userEmail).orElseThrow(EntityNotFoundException::new);
    }
}
