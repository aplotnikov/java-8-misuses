package io.github.aplotnikov.java_8_misuses.stream.collectors;

import io.github.aplotnikov.java_8_misuses.domain.Client;
import io.github.aplotnikov.java_8_misuses.domain.Loan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.math.BigDecimal.ZERO;
import static java.util.Comparator.comparing;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toMap;

class CollectorsChain {

    @Ugly
    class GroupByAndTransformResultingMap {
        Map<Client, BigDecimal> getMaxLoanAmountByClient(List<Client> clients) {
            return clients.stream()
                          .collect(toMap(
                                  identity(),
                                  client -> client.getLoans()
                                                  .stream()
                                                  .map(Loan::getAmount)
                                                  .reduce(ZERO, BigDecimal::max)
                                   )
                          );
        }
    }

    @Ugly
    class GroupByWithMaxCollectorUnwrappingOptionalWithFinisher {
        Map<Client, BigDecimal> getMaxLoanAmountByClient(List<Client> clients) {
            return clients.stream()
                          .map(Client::getLoans)
                          .flatMap(List::stream)
                          .collect(groupingBy(
                                  Loan::getClient,
                                  collectingAndThen(maxBy(comparing(Loan::getAmount)), loan -> loan.map(Loan::getAmount).orElse(ZERO)))
                          );
        }
    }

    @Good
    class CollectToMapWithMergeFunction {
        Map<Client, BigDecimal> getMaxLoanAmountByClient(List<Client> clients) {
            return clients.stream()
                          .map(Client::getLoans)
                          .flatMap(List::stream)
                          .collect(toMap(
                                  Loan::getClient,
                                  Loan::getAmount,
                                  BigDecimal::max
                          ));
        }
    }

    @Good
    class ApplyReduceCollectorAsDownstream {
        Map<Client, BigDecimal> getMaxLoanAmountByClient(List<Client> clients) {
            return clients.stream()
                          .map(Client::getLoans)
                          .flatMap(List::stream)
                          .collect(groupingBy(
                                  Loan::getClient,
                                  mapping(Loan::getAmount, reducing(ZERO, BigDecimal::max)))
                          );
        }
    }
}
