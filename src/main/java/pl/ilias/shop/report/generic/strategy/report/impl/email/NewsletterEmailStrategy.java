package pl.ilias.shop.report.generic.strategy.report.impl.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.ilias.shop.report.EmailType;
import pl.ilias.shop.report.generic.strategy.report.EmailStrategy;

@Slf4j
@Component
public class NewsletterEmailStrategy implements EmailStrategy {

    @Override
    public EmailType getType() {
        return EmailType.NEWSLETTER;
    }

    @Override
    public byte[] generate() {
        log.info("NEWSLETTER generated");
        return new byte[0];
    }
}
