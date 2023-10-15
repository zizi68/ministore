package com.ministore.android.util;

public enum Constants {
    VALIDATION_SUCCESS("Thành công!"),
    VALIDATION_FAIL("Thất bại!"),

    VALIDATION_MESSAGE_401("Tên đăng nhập hoặc mật khẩu của bạn không chính xác!"),
    VALIDATION_INFO_E001("Hãy nhập đầy đủ các thông tin!");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
