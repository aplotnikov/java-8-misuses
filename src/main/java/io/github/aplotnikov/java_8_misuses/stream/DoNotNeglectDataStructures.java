package io.github.aplotnikov.java_8_misuses.stream;

import io.github.aplotnikov.java_8_misuses.domain.Application;
import io.github.aplotnikov.java_8_misuses.domain.Application.Type;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.util.stream.Collectors.toList;

class DoNotNeglectDataStructures {

    @Ugly
    class StateIsStoredInBadDataStructure {

        private final List<Application> applications = new ArrayList<>();

        void applyForLoan(Application application) {
            applications.add(application);
        }

        List<Application> findApplicationBy(Type type) {
            return applications.stream()
                               .filter(application -> application.getType() == type)
                               .collect(toList());
        }
    }

    @Good
    class InternalDataStructureMayBeOptimizedForAccessMethods {

        private final Map<Type, List<Application>> applications = new EnumMap<>(Type.class);

        void applyForLoan(Application application) {
            applications.computeIfAbsent(application.getType(), type -> new ArrayList<>())
                        .add(application);
        }

        List<Application> findApplicationBy(Type type) {
            return applications.get(type);
        }
    }
}
