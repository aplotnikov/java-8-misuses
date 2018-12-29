package io.github.aplotnikov.java_8_misuses.lambda;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Bad;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;

class ClassDesign {

    @Bad
    static class AmbiguousOverloadedMethods {

        interface AmbiguousService<T> {
            T process(Callable<T> function);

            T process(Runnable function);

            T process(UnaryOperator<T> function);

            <R> R process(Function<T, R> function);
        }

        void usage(AmbiguousService<String> service, Supplier<String> supplier) {
            //which method you intended to call??? both are acceptable.
            service.process(() -> {
                supplier.get();
            });
            service.process(() -> supplier.get());
            service.process(String::toUpperCase);
        }
    }

    @Good
    static class SeparateSpecializedMethods {

        interface ClearService<T> {
            <R> R processAsync(Callable<T> function);

            T process(Runnable function);

            T map(UnaryOperator<T> function);

            <R> R convert(Function<T, R> function);
        }

        void usage(ClearService<String> service, Supplier<String> supplier) {
            service.process(supplier::get);
            service.map(String::toUpperCase);
        }
    }
}
