package io.github.aplotnikov.java_8_misuses.stream.collectors;

import io.github.aplotnikov.java_8_misuses.domain.Client;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Bad;

import java.util.List;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.util.Comparator.comparingInt;
import static java.util.function.BinaryOperator.maxBy;

class TrueFunctionalApproach {
    @Ugly
    class BeforeJava8 {
        Client findTheOldestClient(List<Client> clients) {
            if (clients.isEmpty()) {
                return null;
            }

            Client oldestClient = clients.iterator().next();
            for (Client client : clients) {
                if (client.getAge() > oldestClient.getAge()) {
                    oldestClient = client;
                }
            }

            return oldestClient;
        }
    }

    @Ugly
    class NaiveStreamsApproach {
        Client findTheOldestClient(List<Client> clients) {
            return clients.stream()
                          .sorted(comparingInt(Client::getAge).reversed())
                          .findFirst()
                          .orElse(null);
        }
    }

    @Ugly
    class StreamsWithReduction {
        Client findTheOldestClient(List<Client> clients) {
            return clients.stream()
                          .reduce((firstClient, secondClient) ->
                                          firstClient.getAge() > secondClient.getAge() ? firstClient : secondClient
                          )
                          .orElse(null);
        }
    }

    @Bad
    class StreamsWithReductionAndMax {
        Client findTheOldestClient(List<Client> clients) {
            return clients.stream()
                          .reduce(maxBy(comparingInt(Client::getAge)))
                          .orElse(null);
        }
    }

    @Good
    class MaxWithComparator {
        Client findTheOldestClient(List<Client> clients) {
            return clients.stream()
                          .max(comparingInt(Client::getAge))
                          .orElse(null);
        }
    }
}
