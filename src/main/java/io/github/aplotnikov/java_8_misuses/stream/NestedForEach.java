package io.github.aplotnikov.java_8_misuses.stream;

import io.github.aplotnikov.java_8_misuses.domain.Client;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableSet;

class NestedForEach {

    @Ugly
    class NestedForEachWithExternalCollection {
        Set<Long> retrieveClientIdsWithDebts(List<Client> clients) {
            Set<Long> ids = new HashSet<>();
            clients.forEach(
                    client -> {
                        if (client.hasDebt()) {
                            ids.add(client.getId());
                        }
                    }
            );
            return ids;
        }
    }

    @Good
    class StreamOperationsChain {
        Set<Long> retrieveClientIdsWithDebts(List<Client> clients) {
            return clients.stream()
                          .filter(Client::hasDebt)
                          .map(Client::getId)
                          .collect(toSet());
        }
    }

    @Good
    class StreamOperationsChainWithImmutableCollection {
        Set<Long> retrieveClientIdsWithDebts(List<Client> clients) {
            return clients.stream()
                          .filter(Client::hasDebt)
                          .map(Client::getId)
                          .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
        }
    }

    @Good
    class StreamOperationsChainWithImmutableCollector {
        Set<Long> retrieveClientIdsWithDebts(List<Client> clients) {
            return clients.stream()
                          .filter(Client::hasDebt)
                          .map(Client::getId)
                          .collect(toUnmodifiableSet());
        }
    }
}
