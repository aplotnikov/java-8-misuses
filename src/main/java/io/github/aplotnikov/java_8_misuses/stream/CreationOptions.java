package io.github.aplotnikov.java_8_misuses.stream;

import io.github.aplotnikov.java_8_misuses.domain.Application.Type;
import io.github.aplotnikov.java_8_misuses.domain.Loan;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Bad;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.github.aplotnikov.java_8_misuses.domain.Application.Type.EXTENSION;
import static io.github.aplotnikov.java_8_misuses.domain.Application.Type.MAIN;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

class CreationOptions {

    @Ugly
    Stream<Type> getStreamOfApplicationTypesFromArrayAsList() {
        return Arrays.asList(MAIN, EXTENSION).stream();
    }

    @Ugly
    Stream<Type> getStreamOfApplicationTypesForFactoryMethod() {
        return List.of(MAIN, EXTENSION).stream();
    }

    @Good
    Stream<Type> getStreamFromElements() {
        return Stream.of(MAIN, EXTENSION);
    }

    @Ugly
    Stream<Loan> generateStreamByMappingCopies(int n) {
        return Collections.nCopies(n, "ignored")
                          .stream()
                          .map(ignored -> new Loan());
    }

    @Ugly
    Stream<Loan> generateStreamFromRange(int n) {
        return IntStream.range(0, n).mapToObj(ignored -> new Loan());
    }

    @Good
    Stream<Loan> generateStreamFromSupplierWithLimit(int n) {
        return Stream.generate(Loan::new).limit(n);
    }

    @Ugly
    Stream<Loan> generateStreamFromArrayWithRange(Loan[] loans, int max) {
        int to = Integer.min(loans.length, max);
        return IntStream.range(0, to).mapToObj(i -> loans[i]);
    }

    @Bad
    Stream<Loan> generateStreamFromArrayStreamWithRange(Loan[] loans, int max) {
        int to = Integer.min(loans.length, max);
        return Arrays.stream(loans, 0, to);
    }

    @Good
    Stream<Loan> generateStreamFromArrayWithLimit(Loan[] loans, int max) {
        return Stream.of(loans).limit(max);
    }
}
