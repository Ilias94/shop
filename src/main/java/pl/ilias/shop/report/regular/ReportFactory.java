package pl.ilias.shop.report.regular;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ilias.shop.report.FileType;
import pl.ilias.shop.report.regular.strategy.ReportStrategy;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReportFactory {
    private final List<ReportStrategy> reportStrategies;
    private Map<FileType, ReportStrategy> strategyMap;

    @PostConstruct
    void init() {
        strategyMap = reportStrategies.stream()
                .collect(Collectors.toMap(ReportStrategy::getType, Function.identity()));
    }

    public ReportStrategy getStrategyByType(FileType fileType) {
        return strategyMap.get(fileType);
    }
}
