package io.github.aplotnikov.java_8_misuses.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Data
@FieldDefaults(level = PRIVATE)
public class Loan {

    long id;

    Application application;

}
