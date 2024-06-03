package tlu.cse.android.ht63.coffee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tlu.cse.android.ht63.coffee.adapter.CartsAdapter;
import tlu.cse.android.ht63.coffee.database.OrderDatabase;
import tlu.cse.android.ht63.coffee.database.UserDAO;
import tlu.cse.android.ht63.coffee.database.UserDatabase;
import tlu.cse.android.ht63.coffee.model.Cart;
import tlu.cse.android.ht63.coffee.model.Order;
import tlu.cse.android.ht63.coffee.model.User;

public class CartsActivity extends AppCompatActivity {
    private RecyclerView rvProduct;
    private TextView tvTotal, tvResult;
    private ImageView imgexit;
    private EditText etEmployeeId;
    private Button btnCalculate;
    private Cart cart = new Cart();
    private OrderDatabase orderDb;
    private UserDatabase userDb;
    private UserDAO userDAO;
    private User userNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        userDb = UserDatabase.getInstance(this);
        userDAO = userDb.userDAO();

        userNow = (User) getIntent().getSerializableExtra("User");
        imgexit = findViewById(R.id.imgexit);
        imgexit.setOnClickListener(v -> {
            Intent intent = new Intent(CartsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        tvTotal = findViewById(R.id.tvTotal);
        tvResult = findViewById(R.id.tvResult);
        rvProduct = findViewById(R.id.rvproduct);
        etEmployeeId = findViewById(R.id.etEmployeeId);
        btnCalculate = findViewById(R.id.btnCalculate);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvProduct.setLayoutManager(layoutManager);

        CartsAdapter rvAdapter = new CartsAdapter(this, cart);
        rvProduct.setAdapter(rvAdapter);
        tvTotal.setText(String.valueOf(cart.getTotalPrice()));

        orderDb = Room.databaseBuilder(getApplicationContext(), OrderDatabase.class, "orders-db").build();

        etEmployeeId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    getEmployeeDiscount(s.toString().trim());
                } else {
                    cart.setDiscount(0f);
                }
                updateData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnCalculate.setOnClickListener(v -> calculateAndSaveOrder());
    }

    private void getEmployeeDiscount(String employeename) {
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = userDb.userDAO().checkUser(employeename);
            if (user != null && "employee".equals(user.getRole())) {
                cart.setDiscount(0.15f);
            } else {
                cart.setDiscount(0f);
            }
            runOnUiThread(this::updateData);
        });
    }

    private void calculateAndSaveOrder() {
        String discountCode = etEmployeeId.getText().toString().trim();
        // Kiểm tra xem người dùng đã nhập mã giảm giá hay chưa
        if (!discountCode.isEmpty()) {
            // Người dùng đã nhập mã giảm giá, kiểm tra và tính toán giảm giá
                if (userNow != null && "Employee".equals(userNow.getRole())) {
                    String generatedDiscountCode = generateDiscountCode(userNow);
                    if (generatedDiscountCode.equalsIgnoreCase(discountCode)) {
                        // Mã giảm giá hợp lệ, tính toán giảm giá và lưu vào cơ sở dữ liệu
                        float totalPrice = cart.calculateTotalPriceWithDiscount();
                        handleDiscountCode(generatedDiscountCode, totalPrice);
                        saveOrder(discountCode, totalPrice, totalPrice);
                        runOnUiThread(() -> Toast.makeText(CartsActivity.this, "Đã hoàn thành tính toán hóa đơn", Toast.LENGTH_SHORT).show());
                    } else {
                        // Mã giảm giá không hợp lệ, thông báo lỗi
                        runOnUiThread(() -> Toast.makeText(CartsActivity.this, "Mã giảm giá không hợp lệ", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    // Mã giảm giá không hợp lệ, thông báo lỗi
                    runOnUiThread(() -> Toast.makeText(CartsActivity.this, "Mã giảm giá không hợp lệ", Toast.LENGTH_SHORT).show());
                }
        } else {
            // Người dùng không nhập mã giảm giá, tính toán tổng tiền và lưu vào cơ sở dữ liệu
            float totalPrice = cart.calculateTotalPriceWithDiscount();
            saveOrder("", totalPrice, totalPrice);
            Toast.makeText(this, "Đã lưu đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }

    public String generateDiscountCode(User user) {
        if (user != null) {
            return user.getUsername
                    () + "DC"; // Trả về tên nhân viên kèm theo "DC"
        }
        return "";
    }

    private void handleDiscountCode(String discountCode, float totalPrice) {
        float discount = 0.15f; // Giả sử discount là 15%
        float totalPriceWithDiscount = totalPrice * (1 - discount);
        tvResult.setText(String.valueOf(totalPriceWithDiscount));

        // Cập nhật giá trị discount vào adapter
        ((CartsAdapter) rvProduct.getAdapter()).setDiscount(discount);

        // Lưu đơn hàng
        saveOrder(discountCode, totalPrice, totalPriceWithDiscount);
    }

    private void saveOrder(String discountCode, float totalPrice, float totalPriceWithDiscount) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Order order = new Order(discountCode, totalPrice, totalPriceWithDiscount);
            orderDb.orderDao().insert(order);

            runOnUiThread(() ->
                    Toast.makeText(CartsActivity.this, "Order saved successfully", Toast.LENGTH_SHORT).show()
            );
        });
    }

    public void updateData() {
        float totalPrice = cart.getTotalPrice();
        float discount = cart.getDiscount();
        float totalPriceWithDiscount = totalPrice * (1 - discount);

        tvTotal.setText(String.valueOf(totalPrice));
        tvResult.setText(String.valueOf(totalPriceWithDiscount));
    }
}
