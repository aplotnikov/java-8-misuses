package io.github.aplotnikov.java_8_misuses.stream;

import io.github.aplotnikov.java_8_misuses.domain.Application;
import io.github.aplotnikov.java_8_misuses.domain.Client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.github.aplotnikov.java_8_misuses.domain.Application.Type;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

class MatchElementInFunctionalStyle {

    private final Set<Client> clients = new HashSet<>();

    @Ugly
    class UseOldSchoolIterationsWithForEachAndExternalBoolean {
        boolean hasAnyExtensionApplication(Type type) {
            AtomicBoolean found = new AtomicBoolean();
            clients.forEach(
                    client -> client.getApplications().forEach(
                            application -> {
                                if (application.getType().equals(type)) {
                                    found.set(true);
                                }
                            }
                    )
            );
            return found.get();
        }
    }

    @Ugly
    class TryToUseFunctionalStyleWithStreamFilter {
        boolean hasAnyExtensionApplication(Type type) {
            return clients.stream()
                          .filter(
                                  client -> client.getApplications().stream()
                                                  .filter(application -> application.getType().equals(type))
                                                  .count() > 0
                          )
                          .findFirst()
                          .isPresent();
        }
    }

    @Ugly
    class TryToUseStreamMatching {
        boolean hasAnyExtensionApplication(Type type) {
            return clients.stream()
                          .anyMatch(
                                  client -> client.getApplications()
                                                  .stream()
                                                  .anyMatch(application -> application.getType().equals(type))
                          );
        }
    }

    @Good
    class UseFlatMapForSubCollections {
        boolean hasAnyExtensionApplication(Type type) {
            return clients.stream()
                          .flatMap(client -> client.getApplications().stream())
                          .anyMatch(application -> application.getType().equals(type));
        }
    }

    @Good
    class UseFlatMapWithMethodReferencesForSubCollections {
        boolean hasAnyExtensionApplication(Type type) {
            return clients.stream()
                          .map(Client::getApplications)
                          .flatMap(List::stream)
                          .anyMatch(application -> application.getType().equals(type));
        }
    }

    @Good
    class UseFlatMapWithMethodReferencesForSubCollectionsAndUsingMethodReferences {
        boolean hasAnyExtensionApplication(Type type) {
            return clients.stream()
                          .map(Client::getApplications)
                          .flatMap(List::stream)
                          .map(Application::getType)
                          .anyMatch(type::equals);
        }
    }
}
