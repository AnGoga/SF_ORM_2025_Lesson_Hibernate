package ru.mephi.hibernate_1.__mapstruct_ex.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO для краткого отображения данных заказа
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderShortResponse {
    private Long id;
    private String customerName;
    private List<ProductShortResponse> products;
    private Integer productCount;
}