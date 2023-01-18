package pl.ilias.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ilias.shop.report.EmailType;
import pl.ilias.shop.report.generic.GenericFactory;
import pl.ilias.shop.report.generic.strategy.report.EmailStrategy;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {
    private final GenericFactory<EmailType, EmailStrategy> genericFactory;

    @GetMapping
    void generateEmail(@RequestParam EmailType emailType) {
        genericFactory.getStrategyByType(emailType).generate();
    }
}
