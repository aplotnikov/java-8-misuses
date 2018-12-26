package io.github.aplotnikov.java_8_misuses.domain;

import lombok.Value;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Value
public class Client {

    long id;

    public Loan getLastLoan() {
        return null;
    }

    public Optional<Loan> findLastLoan() {
        return Optional.empty();
    }

    public List<Application> getApplications() {
        return emptyList();
    }

    public boolean hasDebt() {
        return false;
    }
}
