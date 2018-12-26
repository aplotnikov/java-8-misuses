package io.github.aplotnikov.java_8_misuses.optional;

import io.github.aplotnikov.java_8_misuses.domain.Loan;

import java.util.Optional;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.util.Objects.nonNull;

class OptionalOverEngineering {

    @Ugly
    class NullProtectionOverEngineering {
        Loan copy(Loan loan) {
            Loan copy = new Loan();

            copy.setId(loan.getId());
            Optional.ofNullable(loan.getApplication()).ifPresent(copy::setApplication);

            return copy;
        }
    }

    @Good
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
