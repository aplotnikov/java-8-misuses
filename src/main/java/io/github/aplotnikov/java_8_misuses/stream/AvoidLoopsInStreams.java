package io.github.aplotnikov.java_8_misuses.stream;

import io.github.aplotnikov.java_8_misuses.domain.Client;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

class AvoidLoopsInStreams {

    @Ugly
    class UseExternalCounter {
        double countAverageLoansPerClient(List<Client> clients) {
            if (clients.isEmpty()) {
                return 0;
            }
            AtomicInteger totalCount = new AtomicInteger();
            clients.forEach(client -> totalCount.addAndGet(client.getLoans().size()));
            return totalCount.doubleValue() / clients.size();
        }
    }

    @Good
    class ApplyMappingsToTargetType {
        double countAverageLoansPerClient(List<Client> clients) {
            return clients.stream()
                          .mapToDouble(client -> client.getLoans().size())
                          .average()
                          .orElse(0);
        }
    }
}
