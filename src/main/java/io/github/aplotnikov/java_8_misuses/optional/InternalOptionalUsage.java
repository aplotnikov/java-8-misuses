package io.github.aplotnikov.java_8_misuses.optional;

import io.github.aplotnikov.java_8_misuses.domain.Client;
import io.github.aplotnikov.java_8_misuses.domain.NotificationService;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;

import java.util.Optional;

import static java.util.Objects.nonNull;

class InternalOptionalUsage {

    @Ugly
    class UnclearOptionalDependencyWithCheckForNull {

        private NotificationService notificationService;

        void process(Client client) {
            if (nonNull(notificationService)) {
                notificationService.sendSms(client);
            }
        }

        void setNotificationService(NotificationService notificationService) {
            this.notificationService = notificationService;
        }
    }

    @Good
    class ValidInternalOptionalDependency {

        private Optional<NotificationService> optionalNotificationService = Optional.empty();

        void process(Client client) {
            optionalNotificationService.ifPresent(service -> service.sendSms(client));
        }

        void setNotificationService(NotificationService notificationService) {
            optionalNotificationService = Optional.ofNullable(notificationService);
        }
    }
}
