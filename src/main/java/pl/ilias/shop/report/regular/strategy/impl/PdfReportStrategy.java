package pl.ilias.shop.report.regular.strategy.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.ilias.shop.report.FileType;
import pl.ilias.shop.report.regular.strategy.ReportStrategy;

@Component
@Slf4j
public class PdfReportStrategy implements ReportStrategy {
    @Override
    public FileType getType() {
        return FileType.PDF;
    }

    @Override
    public byte[] generate() {
        log.info("Generating PDF");
        return new byte[0];
    }
}
