package pl.ilias.shop.report.regular.strategy;

import pl.ilias.shop.report.FileType;

public interface ReportStrategy {
    FileType getType();

    byte[] generate();
}
