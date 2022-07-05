package pl.ilias.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ilias.shop.model.dao.User;
import pl.ilias.shop.service.UserService;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;

    public boolean hasAccessToUser(Long id) {
        User user = userService.currentLoginUser();
        return user.getId().equals(id);
    }
}
