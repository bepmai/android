package tlu.cse.android.ht63.coffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tlu.cse.android.ht63.coffee.adapter.CartAdapter;
import tlu.cse.android.ht63.coffee.models.Cart;

public class CartActivity extends AppCompatActivity {

    private Cart cart;
    ImageButton backBtn;
    RecyclerView recyclerView;
    EditText inputCode;
    Button checkCodebtn;
    TextView totalPrice,roleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        cart = new Cart();

        recyclerView = findViewById(R.id.Acticart_recyclerview);
        totalPrice = findViewById(R.id.Acticart_total);
        backBtn =findViewById(R.id.AcBackBtn);
        roleView = findViewById(R.id.cart_role);
        inputCode = findViewById(R.id.cart_inputCode);
        checkCodebtn = findViewById(R.id.cart_checkCode);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        CartAdapter adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        String  roletext = intent.getStringExtra("ROLE");
        roleView.setText(roletext);



        checkCodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codee = inputCode.getText().toString();
                Intent intent = getIntent();
                String  roletext = intent.getStringExtra("ROLE");
                String  code = intent.getStringExtra("CODE");
                Toast.makeText(CartActivity.this,""+roletext.equals("Employee")+""+codee.equals((String) code)+"" , Toast.LENGTH_SHORT).show();
                if (roletext == "Employee" && codee.equals(code)) {
                    Double discountedPrice = cart.getTotalPrice() * 0.85;
                    totalPrice.setText(String.format("%.2f", discountedPrice));
                }
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.putExtra("ROLE", roletext);
                startActivity(intent1);
            }
        });
        updateTotalPrice();
    }
    public void updateTotalPrice() {
        totalPrice.setText(String.format("%.2f", cart.getTotalPrice()));

    }
}