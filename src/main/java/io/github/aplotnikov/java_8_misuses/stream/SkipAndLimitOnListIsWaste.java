package io.github.aplotnikov.java_8_misuses.stream;

import io.github.aplotnikov.java_8_misuses.domain.Client;

import java.util.List;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

class SkipAndLimitOnListIsWaste {

    @Ugly
    class SkipSomeElementsAndThenTakeSomeForProcessing {
        void registerClients(List<Client> clients) {
            clients.stream()
                   .skip(5)
                   .limit(10)
                   .forEach(this::registerClient);
        }

        private void registerClient(Client client) {
            //register client
        }
    }

    @Good
    class SublistDoNotWasteProcessingTime {
        void registerClients(List<Client> clients) {
            clients.subList(5, 15)
                   .forEach(this::registerClient);
        }

        private void registerClient(Client client) {
            //register client
        }
    }
}
