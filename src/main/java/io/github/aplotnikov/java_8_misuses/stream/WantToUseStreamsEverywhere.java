package io.github.aplotnikov.java_8_misuses.stream;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Bad;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

class WantToUseStreamsEverywhere {
    @Ugly
    class UseStreamToBuildMap {
        Map<String, Object> getJpaProperties() {
            return Stream.of(
                    new SimpleEntry<>("hibernate.show_sql", "true"),
                    new SimpleEntry<>("hibernate.format_sql", "true")
            ).collect(collectingAndThen(
                    toMap(Map.Entry::getKey, Map.Entry::getValue),
                    Collections::unmodifiableMap)
            );
        }
    }

    @Bad
    class UseOldPlainMap {
        Map<String, Object> getJpaProperties() {
            Map<String, Object> properties = new HashMap<>();
            properties.put("hibernate.show_sql", "true");
            properties.put("hibernate.format_sql", "true");
            return Collections.unmodifiableMap(properties);
        }
    }

    @Good
    class UseOldPlainMapWithCollectionFactoryMethods {
        Map<String, Object> getJpaProperties() {
            return Map.of(
                    "hibernate.show_sql", "true",
                    "hibernate.format_sql", "true"
            );
        }
    }
}
