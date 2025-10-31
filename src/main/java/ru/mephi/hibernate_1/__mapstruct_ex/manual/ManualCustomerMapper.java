package ru.mephi.hibernate_1.__mapstruct_ex.manual;

import ru.mephi.hibernate_1.__mapstruct_ex.dto.request.CustomerRequest;
import ru.mephi.hibernate_1.__mapstruct_ex.dto.response.CustomerResponse;
import ru.mephi.hibernate_1.__mapstruct_ex.model.Customer;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


public class ManualCustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setEmail(customer.getEmail());
        response.setFullName(createFullName(customer));

        return response;
    }

    public Customer toEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setMiddleName(request.getMiddleName());
        customer.setEmail(request.getEmail());
        customer.setPasswordHash(hashPassword(request.getPassword()));

        return customer;
    }

    public void updateUser(CustomerRequest request, Customer customer) {
        if (request == null || customer == null) {
            return;
        }
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setMiddleName(request.getMiddleName());
        customer.setEmail(request.getEmail());
        customer.setPasswordHash(hashPassword(request.getPassword()));
    }

    public Customer createAdminToken(Customer customer) {
        if (customer == null) {
            return null;
        }

        customer.setAdminToken(UUID.randomUUID().toString());
        return customer;
    }

    private String hashPassword(String password) {
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

    private String createFullName(Customer customer) {
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