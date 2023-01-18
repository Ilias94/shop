package pl.ilias.shop.report.generic.strategy.report.impl.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.ilias.shop.report.EmailType;
import pl.ilias.shop.report.generic.strategy.report.EmailStrategy;

@Slf4j
@Component
public class NewProductEmailStrategy implements EmailStrategy {
    @Override
    public EmailType getType() {
        return EmailType.NEW_PRODUCT;
    }

    @Override
    public byte[] generate() {
        log.info("NEW_PRODUCT generated");
        return new byte[0];
    }
}
