package pl.ilias.shop.report.generic.strategy.report;

import pl.ilias.shop.report.FileType;
import pl.ilias.shop.report.generic.strategy.GenericStrategy;

public interface FileStrategy extends GenericStrategy<FileType> {
    byte[] generate();
}
