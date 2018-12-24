package io.github.aplotnikov.java_8_misuses.domain;

import lombok.Value;

import java.util.Optional;

@Value
public class Client {

    long id;

    public Loan getLastLoan() {
        return null;
    }

    public Optional<Loan> findLastLoan() {
        return Optional.empty();
    }
}
