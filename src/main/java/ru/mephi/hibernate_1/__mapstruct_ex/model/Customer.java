package ru.mephi.hibernate_1.__mapstruct_ex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String passwordHash;
    private String adminToken;
}