package io.github.aplotnikov.java_8_misuses.lambda;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Bad;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;

class ClassDesign {

    @Bad
    static class AmbiguousOverloadedMethods {

        interface AmbiguousService<T> {
            T process(Callable<T> function);

            T process(Runnable function);
        }

        void usage(AmbiguousService<String> service, Supplier<String> supplier) {
            //which method you intended to call??? both are acceptable.
            service.process(() -> { supplier.get(); });
            service.process(() -> supplier.get());
        }
    }

    @Good
    static class SeparateSpecializedMethods {

        interface ClearService<T> {
            <R> R processAsync(Callable<T> function);

            T process(Runnable function);
        }

        void usage(ClearService<String> service, Supplier<String> supplier) {
            service.process(supplier::get);
        }
    }
}
