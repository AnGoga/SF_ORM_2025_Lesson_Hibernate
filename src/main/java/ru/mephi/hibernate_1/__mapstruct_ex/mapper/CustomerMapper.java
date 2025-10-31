package ru.mephi.hibernate_1.__mapstruct_ex.mapper;

import org.mapstruct.*;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.request.CustomerRequest;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.response.CustomerResponse;
import ru.mephi.hibernate_1.__mapstruct_ex.model.Customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

    @Mapping(target = "fullName", expression = "java(createFullName(customer))")
    CustomerResponse toResponse(Customer customer);

    @Mapping(target = "passwordHash", expression = "java(hashPassword(request.getPassword()))")
    @Mapping(target = "adminToken", ignore = true) // можно не ставить т.к. у нас unmappedTargetPolicy = ReportingPolicy.IGNORE
    Customer toEntity(CustomerRequest request);

    @Mapping(target = "passwordHash", expression = "java(request.getPassword() != null ? hashPassword(request.getPassword()) : customer.getPasswordHash())")
    void updateCustomer(CustomerRequest request, @MappingTarget Customer customer);

    default Customer createAdminToken(Customer customer) {
        if (customer == null) {
            return null;
        }
        customer.setAdminToken(UUID.randomUUID().toString());
        return customer;
    }


    default String hashPassword(String password) {
        if (password == null) {
            return null;
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка хеширования пароля", e);
        }
    }

    default String createFullName(Customer customer) {
        if (customer == null) {
            return null;
        }

        StringBuilder fullName = new StringBuilder();

        if (customer.getLastName() != null) {
            fullName.append(customer.getLastName());
        }

        if (customer.getFirstName() != null) {
            if (fullName.length() > 0) {
                fullName.append(" ");
            }
            fullName.append(customer.getFirstName());
        }

        if (customer.getMiddleName() != null) {
            if (fullName.length() > 0) {
                fullName.append(" ");
            }
            fullName.append(customer.getMiddleName());
        }

        return fullName.toString();
    }
}