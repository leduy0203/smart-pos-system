package com.smartpos.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(403, "Access denied", HttpStatus.FORBIDDEN),

    TOKEN_INVALID(401, "Token invalid", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(401, "Token expired", HttpStatus.UNAUTHORIZED),

    REFRESH_TOKEN_INVALID(401, "Refresh token invalid", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(401, "Refresh token expired", HttpStatus.UNAUTHORIZED),

    LOGIN_FAILED(400, "Username or password is incorrect", HttpStatus.BAD_REQUEST),


    USER_ALREADY_EXISTS(400, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(404, "User not found", HttpStatus.NOT_FOUND),

    USER_DISABLED(400, "User is disabled", HttpStatus.BAD_REQUEST),

    ROLE_NOT_FOUND(404, "Role not found", HttpStatus.NOT_FOUND),

    ROLE_ALREADY_EXITS(404, "Role already exits", HttpStatus.BAD_REQUEST),


    CATEGORY_NOT_FOUND(404, "Category not found", HttpStatus.NOT_FOUND),

    CATEGORY_ALREADY_EXISTS(409, "Category already exits", HttpStatus.CONFLICT),

    PRODUCT_NOT_FOUND(404, "Product not found", HttpStatus.NOT_FOUND),

    PRODUCT_UNAVAILABLE(400, "Product is unavailable", HttpStatus.BAD_REQUEST),

    INGREDIENT_NOT_FOUND(404, "Ingredient not found", HttpStatus.NOT_FOUND),

    INSUFFICIENT_INGREDIENT_STOCK(
            400,
            "Insufficient ingredient stock",
            HttpStatus.BAD_REQUEST
    ),

    SUPPLIER_NOT_FOUND(404, "Supplier not found", HttpStatus.NOT_FOUND),
    SUPPLIER_ALREADY_EXISTS(400, "Supplier already exists", HttpStatus.CONFLICT),
    SUPPLIER_PHONE_ALREADY_EXISTS(400, "Supplier phone already exists", HttpStatus.CONFLICT),


    TABLE_NOT_FOUND(404, "Table not found", HttpStatus.NOT_FOUND),
    TABLE_NUMBER_ALREADY_EXISTS(409 , "Table number already exists", HttpStatus.CONFLICT),

    TABLE_OCCUPIED(400, "Table is occupied", HttpStatus.BAD_REQUEST),

    TABLE_NOT_AVAILABLE(400, "Table is not available", HttpStatus.BAD_REQUEST),


    ORDER_NOT_FOUND(404, "Order not found", HttpStatus.NOT_FOUND),

    ORDER_ALREADY_PAID(400, "Order already paid", HttpStatus.BAD_REQUEST),

    ORDER_CANCELLED(400, "Order has been cancelled", HttpStatus.BAD_REQUEST),

    ORDER_ITEM_NOT_FOUND(404, "Order item not found", HttpStatus.NOT_FOUND),

    ORDER_ITEM_ALREADY_SERVED(
            400,
            "Order item already served",
            HttpStatus.BAD_REQUEST
    ),

    INVALID_ORDER_STATUS(
            400,
            "Invalid order status transition",
            HttpStatus.BAD_REQUEST
    ),

    INVALID_ORDER_ITEM_STATUS(
            400,
            "Invalid order item status transition",
            HttpStatus.BAD_REQUEST
    ),

    PAYMENT_NOT_FOUND(404, "Payment not found", HttpStatus.NOT_FOUND),

    PAYMENT_FAILED(400, "Payment failed", HttpStatus.BAD_REQUEST),

    INVALID_PAYMENT_METHOD(
            400,
            "Invalid payment method",
            HttpStatus.BAD_REQUEST
    ),

    DISCOUNT_NOT_FOUND(404, "Discount not found", HttpStatus.NOT_FOUND),

    DISCOUNT_EXPIRED(400, "Discount expired", HttpStatus.BAD_REQUEST),

    DISCOUNT_NOT_ACTIVE(400, "Discount is not active", HttpStatus.BAD_REQUEST),


    INVENTORY_IMPORT_FAILED(
            400,
            "Inventory import failed",
            HttpStatus.BAD_REQUEST
    ),

    INVENTORY_LOG_NOT_FOUND(
            404,
            "Inventory log not found",
            HttpStatus.NOT_FOUND
    ),

    INVALID_REQUEST(400, "Invalid request", HttpStatus.BAD_REQUEST),

    INVALID_QUANTITY(400, "Quantity must be greater than 0", HttpStatus.BAD_REQUEST),

    INVALID_PRICE(400, "Price must be greater than 0", HttpStatus.BAD_REQUEST),


    INTERNAL_SERVER_ERROR(
            500,
            "Internal server error",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),
    PERMISSION_NOT_FOUND(404, "Permission not found" , HttpStatus.NOT_FOUND ),

    PHONE_NUMBER_ALREADY_EXISTS(409, "Phone number already exists", HttpStatus.CONFLICT),

    USERNAME_ALREADY_EXISTS(409, "Username already exists", HttpStatus.CONFLICT),

    EMAIL_ALREADY_EXISTS(409, "Email already exists", HttpStatus.CONFLICT),

    PASSWORD_MISMATCH(400, "Password and confirm password do not match", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
