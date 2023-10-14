package com.moht1.webapi.util;

public enum Constants {
    VALIDATION_SUCCESS("Thành công!"),
    VALIDATION_FAIL("Thất bại!"),
    VALIDATION_NAME_E002("Tên đăng nhập đã được tồn tại"),
    VALIDATION_PASSWORD_E002("Mật khẩu từ 6-40 kí tự"),
    VALIDATION_EMAIL_E002("Email không đúng định dạng"),
    VALIDATION_EMAIL_E003("Email đã tồn tại"),
    VALIDATION_PHONE_E002("Số điện thoại đã được sử dụng"),
    ROLE_404("Role is not found");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
