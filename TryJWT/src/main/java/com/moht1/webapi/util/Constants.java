package com.moht1.webapi.util;

public enum Constants {
    VALIDATION_SUCCESS("Thành công!"),
    VALIDATION_FAIL("Thất bại!"),
    VALIDATION_NAME_E002("Tên đăng nhập đã được tồn tại"),
    VALIDATION_PASSWORD_E002("Mật khẩu từ 6-40 kí tự"),
    VALIDATION_EMAIL_E002("Email không đúng định dạng"),
    VALIDATION_EMAIL_E003("Email đã tồn tại"),
    VALIDATION_PHONE_E002("Số điện thoại đã được sử dụng"),
    VALIDATION_PASSWORD_E003("Mật khẩu cũ không đúng"),
    ADDTOCART_SUCCESS("Thêm vào giỏ hàng thành công"),
    CART_CHECK_QUANTITY("Số lượng yêu cầu vượt quá số lượng còn lại của sản phẩm này!"),
    STATUS_INVALID("Trạng thái không tồn tại"),
    ORDER_404("Không tìm thấy đơn đặt hàng"),
    ORDER_DETAIL_404("Không tìm thấy chi tiết đơn đặt hàng"),
    PRODUCT_404("Không tìm thấy sản phẩm"),
    OUT_OF_PRODUCT("Sản phẩm đã được bán hết"),
    CATEGORY_404("Không tìm thấy danh mục"),
    ADDRESS_404("Không tìm thấy địa chỉ"),
    RETURN_404("Không tìm thấy đơn hoàn trả"),
    USER_404("Không tìm thấy user"),
    ROLE_404("Role is not found");

    private final String message;

    Constants(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
