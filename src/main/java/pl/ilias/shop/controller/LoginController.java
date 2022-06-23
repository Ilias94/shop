package pl.ilias.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.ilias.shop.model.dto.LoginDto;
import pl.ilias.shop.model.dto.TokenDto;
import pl.ilias.shop.service.LoginService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    TokenDto login(@RequestBody @Valid LoginDto loginDto) {
        return loginService.login(loginDto);
    }
}
