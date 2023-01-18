package pl.ilias.shop.report.generic.strategy.report;

import pl.ilias.shop.report.EmailType;
import pl.ilias.shop.report.generic.strategy.GenericStrategy;

public interface EmailStrategy extends GenericStrategy<EmailType> {
    byte[] generate();
}
