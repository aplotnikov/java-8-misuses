package io.github.aplotnikov.java_8_misuses.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Application {

    public enum Type {
        MAIN,
        EXTENSION,
        ADDITIONAL
    }

    long id;

    BigDecimal amount;

}
