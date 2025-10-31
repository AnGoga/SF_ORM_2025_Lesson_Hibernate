package ru.mephi.hibernate_1.__mapstruct_ex.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private CustomerResponse customer;
    private List<ProductResponse> products;
    private Integer productCount;
}