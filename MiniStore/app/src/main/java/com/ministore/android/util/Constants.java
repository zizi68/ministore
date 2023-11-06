package com.ministore.android.util;

public enum Constants {
    VALIDATION_SUCCESS("Thành công!"),
    VALIDATION_FAIL("Thất bại!"),

    VALIDATION_MESSAGE_401("Tên đăng nhập hoặc mật khẩu của bạn không chính xác!"),
    VALIDATION_INFO_E001("Hãy nhập đầy đủ các thông tin!"),
    NAME_BLANK("Tên không được bỏ trống!"),
    EMAIL_BLANK("Email không được bỏ trống!"),
    USERNAME_BLANK("Tên đăng nhập không được bỏ trống!"),
    PHONE_BLANK("Số điện thoại không được bỏ trống!"),
    OLD_PASSWORD_BLANK("Mật khẩu cũ không được bỏ trống!"),
    NEW_PASSWORD_BLANK("Mật khẩu mới không được bỏ trống!"),
    CONFIRM_PASSWORD_FAIL("Xác nhận mật khẩu không đúng!"),
    REMOVE_PRODUCT_CART("Đã xóa sản phẩm từ giỏ hàng"),
    UPDATE_QUANTITY_CART("Số lượng sản phẩm trong giỏ hàng đã được cập nhật");


    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
