package io.github.aplotnikov.java_8_misuses.optional;

import io.github.aplotnikov.java_8_misuses.domain.Loan;

import java.util.Optional;

import static java.util.Objects.nonNull;

class OptionalOverEngineering {

    class NullProtectionOverEngineering {
        Loan copy(Loan loan) {
            Loan copy = new Loan();

            copy.setId(loan.getId());
            Optional.ofNullable(loan.getApplication()).ifPresent(copy::setApplication);

            return copy;
        }
    }

    class SimpleConditionalCopying {
        Loan copy(Loan loan) {
            Loan copy = new Loan();

            copy.setId(loan.getId());
            if (nonNull(loan.getApplication())) {
                copy.setApplication(loan.getApplication());
            }

            return copy;
        }
    }
}
