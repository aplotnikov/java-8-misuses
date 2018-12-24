package io.github.aplotnikov.java_8_misuses.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Application {

    long id;

    BigDecimal amount;

}
