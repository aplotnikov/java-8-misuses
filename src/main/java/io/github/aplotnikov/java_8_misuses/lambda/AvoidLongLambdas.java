package io.github.aplotnikov.java_8_misuses.lambda;

import io.github.aplotnikov.java_8_misuses.domain.Client;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.function.Function;

import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Good;
import static io.github.aplotnikov.java_8_misuses.utils.Annotations.Ugly;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

class AvoidLongLambdas {

    @Ugly
    class LongLambdaInPlace {
        List<ClientDto> convertToDto(List<Client> clients) {
            return clients.stream()
                          .map(client -> {
                              ClientDto dto = new ClientDto();
                              dto.setId(client.getId());
                              dto.setName(client.getName());
                              dto.setSurname(client.getSurname());
                              return dto;
                          })
                          .collect(toList());
        }
    }

    @Good
    class MethodReferenceInsteadOfLambda {
        //particular toDto could be implemented as a separate class or as a lambda function
        private final Function<Client, ClientDto> toDto = this::convertToDto;

        List<ClientDto> convertToDto(List<Client> clients) {
            return clients.stream()
                          .map(toDto)
                          .collect(toList());
        }

        private ClientDto convertToDto(Client client) {
            ClientDto dto = new ClientDto();
            dto.setId(client.getId());
            dto.setName(client.getName());
            dto.setSurname(client.getSurname());
            return dto;
        }
    }

    @Data
    @FieldDefaults(level = PRIVATE)
    private static class ClientDto {

        long id;

        String name;

        String surname;

    }
}
