package io.github.aplotnikov.java_8_misuses.stream.collectors;

import io.github.aplotnikov.java_8_misuses.domain.Client;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.IntStream;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.util.stream.Collectors.summarizingInt;

class StatisticsCalculation {

    @Ugly
    class IterateThroughValuesSeveralTimes {
        void findMinAndMaxClientAge(List<Client> clients) {
            getClientAgesStream(clients)
                    .max()
                    .ifPresent(max -> System.out.println("MAX: " + max));

            getClientAgesStream(clients)
                    .min()
                    .ifPresent(min -> System.out.println("MIN: " + min));
        }

        private IntStream getClientAgesStream(List<Client> clients) {
            return clients.stream().mapToInt(Client::getAge);
        }
    }

    @Good
    class CalculateStatisticsInSingleRunWithCollector {
        void findMinAndMaxClientAge(List<Client> clients) {
            IntSummaryStatistics statistics = clients.stream().collect(summarizingInt(Client::getAge));
            System.out.println("MAX: " + statistics.getMax());
            System.out.println("MIN: " + statistics.getMin());
        }
    }
}
