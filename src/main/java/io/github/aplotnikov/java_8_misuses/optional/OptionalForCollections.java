package io.github.aplotnikov.java_8_misuses.optional;

import io.github.aplotnikov.java_8_misuses.domain.Client;
import io.github.aplotnikov.java_8_misuses.domain.Loan;

import java.util.List;
import java.util.Optional;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.util.Collections.emptyList;

class OptionalForCollections {

    @Ugly
    class TooVerbose {

        Loan findLastClientLoan(Client client) {
            Optional<List<Loan>> loans = findClientLoans(client.getId());

            if (loans.isPresent() && !loans.get().isEmpty()) {
                return loans.get().get(0);
            }

            throw new IllegalStateException("No loans were found");
        }

        private Optional<List<Loan>> findClientLoans(long id) {
            //find loans into DB
            return Optional.empty();
        }
    }

    @Good
    class NiceAndClean {

        Loan findLastClientLoan(Client client) {
            return findClientLoans(client.getId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No loans were found"));
        }

        private List<Loan> findClientLoans(long id) {
            //find loans into DB
            return emptyList();
        }
    }
}
