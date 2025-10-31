package ru.mephi.hibernate_1.__mapstruct_ex.mapper;

import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mephi.hibernate_1.__mapstruct_ex.model.Customer;
import ru.mephi.hibernate_1.db.DatabaseInitializerRunner;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.request.OrderRequest;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.response.OrderResponse;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.response.OrderShortResponse;
import ru.mephi.hibernate_1.__mapstruct_ex.model.Order;
import ru.mephi.hibernate_1.__mapstruct_ex.model.Product;

import java.util.List;


@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {CustomerMapper.class, ProductMapper.class}
)
public interface OrderMapper {
    Logger logger = LoggerFactory.getLogger(DatabaseInitializerRunner.class);


    @Mapping(target = "productCount", expression = "java(getProductCount(order))")
    OrderResponse toResponse(Order order);

    @Mapping(target = "customerName", expression = "java(getCustomerName(order))")
    @Mapping(target = "products", expression = "java(productMapper.toShortResponseList(order.getProducts()))")
    @Mapping(target = "productCount", expression = "java(getProductCount(order))")
    OrderShortResponse toShortResponse(Order order, @Context ProductMapper productMapper);


    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "products", source = "products")
    Order toEntity(OrderRequest request, Customer customer, List<Product> products);

    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "products", source = "products")
    void updateOrder(OrderRequest request, Customer customer, List<Product> products, @MappingTarget Order order);


    default String getCustomerName(Order order) {
        if (order == null || order.getCustomer() == null) {
            return null;
        }

        Customer customer = order.getCustomer();
        StringBuilder customerName = new StringBuilder();

        if (customer.getLastName() != null) {
            customerName.append(customer.getLastName());
        }

        if (customer.getFirstName() != null) {
            if (customerName.length() > 0) {
                customerName.append(" ");
            }
            customerName.append(customer.getFirstName().charAt(0)).append(".");
        }

        if (customer.getMiddleName() != null) {
            if (customerName.length() > 0) {
                customerName.append(" ");
            }
            customerName.append(customer.getMiddleName().charAt(0)).append(".");
        }

        return customerName.toString();
    }

    default Integer getProductCount(Order order) {
        if (order == null || order.getProducts() == null) {
            return 0;
        }

        return order.getProducts().size();
    }

    @AfterMapping
    default void afterToResponse(@MappingTarget OrderResponse target, Order source) {
        logger.info("afterToResponse");
    }

    @BeforeMapping
    default void beforeToResponse(@MappingTarget OrderResponse target, Order source) {
        logger.info("beforeToResponse");
    }
}