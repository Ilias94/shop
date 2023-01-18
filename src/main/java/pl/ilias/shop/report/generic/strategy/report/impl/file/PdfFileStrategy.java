package pl.ilias.shop.report.generic.strategy.report.impl.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.ilias.shop.report.FileType;
import pl.ilias.shop.report.generic.strategy.report.FileStrategy;

@Slf4j
@Component
public class PdfFileStrategy implements FileStrategy {
    @Override
    public FileType getType() {
        return FileType.PDF;
    }

    @Override
    public byte[] generate() {
        log.info("PDF Generated");
        return new byte[0];
    }
}
