package io.github.aplotnikov.java_8_misuses.optional;

import io.github.aplotnikov.java_8_misuses.domain.Client;
import io.github.aplotnikov.java_8_misuses.domain.Loan;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

import java.util.concurrent.atomic.AtomicLong;

import static java.util.Optional.ofNullable;

class OptionalOrElse {

    class BeforeJava8 {
        long findLastLoanId(Client client) {
            return client != null && client.getLastLoan() != null
                    ? client.getLastLoan().getId() : -1L;
        }
    }

    @Ugly
    class UsingOptionalIsPresent {
        long findLastLoanId(Client client) {
            if (ofNullable(client).isPresent()) {
                if (ofNullable(client.getLastLoan()).isPresent()) {
                    return client.getLastLoan().getId();
                }
            }
            return -1L;
        }
    }

    @Ugly
    class UsingIfPresentInSameImperativeWayWithDirtyHackOptionalIsPresent {
        long findLastLoanId(Client givenClient) {
            AtomicLong result = new AtomicLong(-1);

            ofNullable(givenClient).ifPresent(
                    client -> client.findLastLoan().ifPresent(
                            loan -> result.set(loan.getId())
                    )
            );

            return result.get();
        }
    }

    @Good
    class UsingOrElse {
        long findLastLoanId(Client client) {
            return ofNullable(client)
                    .map(Client::getLastLoan)
                    .map(Loan::getId)
                    .orElse(-1L);
        }
    }
}
