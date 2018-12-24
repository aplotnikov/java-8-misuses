package io.github.aplotnikov.java_8_misuses.optional;

import io.github.aplotnikov.java_8_misuses.domain.Client;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

class OptionalConstructorParameters {

    @Ugly
    class OptionalLeaksOutsideClass {

        List<Email> prepareEmails(Client client) {
            Email noAttachment = new Email("First!", "No attachment", Optional.empty());
            Email withAttachment = new Email("Second!", "With attachment", Optional.of(new Attachment()));

            return asList(noAttachment, withAttachment);
        }

        @FieldDefaults(makeFinal = true, level = PRIVATE)
        @Getter
        @ToString
        @EqualsAndHashCode
        class Email {
            String subject;
            String body;
            Optional<Attachment> attachment;

            Email(String subject, String body, Optional<Attachment> attachment) {
                this.subject = subject;
                this.body = body;
                this.attachment = attachment;
            }
        }
    }

    @Good
    class OverloadedConstructors {
        List<Email> prepareEmails(Client client) {
            return List.of(
                    new Email("First!", "No attachment"),
                    new Email("Second!", "With attachment", new Attachment())
            );
        }

        @FieldDefaults(makeFinal = true, level = PRIVATE)
        @Getter
        @ToString
        @EqualsAndHashCode
        class Email {
            String subject;
            String body;
            Attachment attachment;

            Email(String subject, String body) {
                this(subject, body, null);
            }

            Email(String subject, String body, Attachment attachment) {
                this.subject = subject;
                this.body = body;
                this.attachment = attachment;
            }

            boolean hasAttachment() {
                return nonNull(attachment);
            }
        }
    }

    class Attachment {}
}
