package pl.ilias.shop.report.generic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ilias.shop.report.generic.strategy.GenericStrategy;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GenericFactory<K, V extends GenericStrategy<K>> {
    private final List<V> strategies;
    private Map<K, V> strategyMap;

    @PostConstruct
    public void init() {
        strategyMap = strategies.stream()
                .collect(Collectors.toMap(GenericStrategy::getType, Function.identity()));
    }

    public V getStrategyByType(K type) {
        return strategyMap.get(type);
    }
}
