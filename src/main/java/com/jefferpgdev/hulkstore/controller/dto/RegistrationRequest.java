package com.jefferpgdev.hulkstore.controller.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userRole;

}
