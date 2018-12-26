package io.github.aplotnikov.java_8_misuses.stream;

import io.github.aplotnikov.java_8_misuses.domain.Client;

import java.util.ArrayList;
import java.util.List;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

class PreferSpecializedStreams {

    private final List<Client> clients = new ArrayList<>();

    @Ugly
    class GeneralStreamUsage {
        int getTotalAge() {
            return clients.stream()
                          .map(Client::getAge)
                          .reduce(0, Integer::sum);
        }
    }

    @Good
    class SpecializedStreamUsage {
        int getTotalAge() {
            return clients.stream()
                          .mapToInt(Client::getAge)
                          .sum();
        }
    }

    @Ugly
    class FlatMapToCountElementsInAllCollections {
        int countLoans(List<Client> clients) {
            return (int) clients.stream()
                                .map(Client::getLoans)
                                .flatMap(List::stream)
                                .count();
        }
    }

    @Good
    class MapToIntToSimplifyCalculation {
        int countLoans(List<Client> clients) {
            return clients.stream()
                          .map(Client::getLoans)
                          .mapToInt(List::size)
                          .sum();
        }
    }
}
