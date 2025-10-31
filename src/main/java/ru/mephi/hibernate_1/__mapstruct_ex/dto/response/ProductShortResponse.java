package ru.mephi.hibernate_1.__mapstruct_ex.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для краткого отображения данных товара
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductShortResponse {
    private Long id;
    private String name;
}