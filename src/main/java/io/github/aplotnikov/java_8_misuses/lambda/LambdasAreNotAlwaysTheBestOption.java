package io.github.aplotnikov.java_8_misuses.lambda;

import java.util.Optional;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

class LambdasAreNotAlwaysTheBestOption {

    @Ugly
    class UnneededLambdasUsage {
        void processAndPrint(String name) {
            Optional.ofNullable(name)
                    .map(s -> s.toUpperCase())
                    .map(s -> doProcess(s))
                    .ifPresent(s -> System.out.print(s));
        }

        private String doProcess(String name) {
            return "MR. " + name;
        }
    }

    @Good
    class MethodReferenceUsage {
        void processAndPrint(String name) {
            Optional.ofNullable(name)
                    .map(String::toUpperCase)
                    .map(this::doProcess)
                    .ifPresent(System.out::print);
        }

        private String doProcess(String name) {
            return "MR. " + name;
        }
    }
}
