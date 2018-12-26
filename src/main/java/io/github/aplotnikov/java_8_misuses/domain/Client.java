package io.github.aplotnikov.java_8_misuses.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Value
@EqualsAndHashCode(exclude = {"loans"})
@ToString(exclude = {"loans"})
public class Client {

    long id;

    String name;

    String surname;

    List<Loan> loans;

    public Client(long id, String name, String surname, List<Loan> loans) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.loans = loans;
        loans.forEach(loan -> loan.setClient(Client.this));
    }

    public Loan getLastLoan() {
        return null;
    }

    public Optional<Loan> findLastLoan() {
        return Optional.empty();
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<Application> getApplications() {
        return emptyList();
    }

    public boolean hasDebt() {
        return false;
    }

    public int getAge() {
        return 30;
    }
}
