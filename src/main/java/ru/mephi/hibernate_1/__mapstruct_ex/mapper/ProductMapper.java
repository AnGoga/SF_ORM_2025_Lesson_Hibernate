package ru.mephi.hibernate_1.__mapstruct_ex.mapper;

import org.mapstruct.*;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.request.ProductRequest;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.response.ProductResponse;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.response.ProductShortResponse;
import ru.mephi.hibernate_1.__mapstruct_ex.model.Product;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    @Mapping(target = "formattedPrice", expression = "java(formatPrice(product.getPrice()))")
    ProductResponse toResponse(Product product);

    ProductShortResponse toShortResponse(Product product);

    Product toEntity(ProductRequest request);


    void updateProduct(ProductRequest request, @MappingTarget Product product);

    List<ProductResponse> toResponseList(List<Product> products);

    List<ProductShortResponse> toShortResponseList(List<Product> products);

    default String formatPrice(BigDecimal price) {
        if (price == null) {
            return null;
        }

        return NumberFormat.getCurrencyInstance(new Locale("ru", "RU")).format(price);
    }
}