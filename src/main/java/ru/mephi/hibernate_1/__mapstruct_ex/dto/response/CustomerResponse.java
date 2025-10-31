package ru.mephi.hibernate_1.__mapstruct_ex.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для отображения данных пользователя
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String fullName; // Комбинация firstName, lastName и middleName
    private String email;
}