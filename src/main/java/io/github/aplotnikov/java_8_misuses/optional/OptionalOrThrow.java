package io.github.aplotnikov.java_8_misuses.optional;

import io.github.aplotnikov.java_8_misuses.domain.Client;
import io.github.aplotnikov.java_8_misuses.domain.Loan;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

import java.util.Optional;

class OptionalOrThrow {

    @Ugly
    class ManualCheckForPresenceToThrowException {
        Loan getLastLoan(Client client) {
            if (client.findLastLoan().isPresent()) {
                return client.findLastLoan().get();
            }

            throw new IllegalStateException("Client does not have any loans");
        }

        private Optional<Loan> findLoan(long id) {
            return Optional.empty();
        }

        void deleteLoan(long id) {
            Optional<Loan> loan = findLoan(id);
            if (loan.isPresent()) {
                delete(loan.get());
            }
        }

        private void delete(Loan loan) {
            // delete loan from DB
        }
    }

    @Good
    class OrElseThrowUsage {
        Loan getLastLoan(Client client) {
            return client.findLastLoan()
                         .orElseThrow(() -> new IllegalStateException("Client does not have any loans"));
        }

        void deleteLoan(long id) {
            findLoan(id).ifPresent(this::delete);
        }

        private Optional<Loan> findLoan(long id) {
            return Optional.empty();
        }

        private void delete(Loan loan) {
            // delete loan from DB
        }
    }
}
