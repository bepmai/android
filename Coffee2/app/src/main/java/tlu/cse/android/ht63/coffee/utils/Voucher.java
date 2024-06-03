package tlu.cse.android.ht63.coffee.utils;

import android.util.Log;


import tlu.cse.android.ht63.coffee.model.User;

public class Voucher {
    public static String generateDiscountCode(User user) {
        String employeeName = user.getEmployeename(); // Không cần chuyển đổi sang chuỗi
        String discountCode = employeeName + "DC";

        // In ra mã giảm giá được tạo ra
        Log.d("Voucher", "Discount code generated: " + discountCode);

        return discountCode;
    }
}

