package pl.ilias.shop.report.generic.strategy.report.impl.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.ilias.shop.report.FileType;
import pl.ilias.shop.report.generic.strategy.report.FileStrategy;

@Slf4j
@Component
public class CsvFileStrategy implements FileStrategy {
    @Override
    public FileType getType() {
        return FileType.CSV;
    }

    @Override
    public byte[] generate() {
        log.info("CSV generated");
        return new byte[0];
    }
}
