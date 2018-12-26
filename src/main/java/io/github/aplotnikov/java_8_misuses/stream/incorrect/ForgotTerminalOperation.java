package io.github.aplotnikov.java_8_misuses.stream.incorrect;

import io.github.aplotnikov.java_8_misuses.domain.Application;
import io.github.aplotnikov.java_8_misuses.domain.Client;

import java.util.List;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Bad;

class ForgotTerminalOperation {
    @Bad
    void processClients(List<Client> clients) {
        clients.stream()
               .map(Client::getApplications)
               .flatMap(List::stream)
               .filter(Application::isExtension);
    }
}
