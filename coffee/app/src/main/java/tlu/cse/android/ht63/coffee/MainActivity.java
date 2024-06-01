package tlu.cse.android.ht63.coffee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tlu.cse.android.ht63.coffee.adapter.ProductAdapter;
import tlu.cse.android.ht63.coffee.data.UserData;
import tlu.cse.android.ht63.coffee.models.Product;
import tlu.cse.android.ht63.coffee.models.User;
import tlu.cse.android.ht63.coffee.repository.ProductRepository;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    static int code;
    static String roleSata; 
    TextView roleView;
    ImageButton main_shopCartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recyclerview);
        main_shopCartBtn = findViewById(R.id.main_shopCartBtn);
        roleView = findViewById(R.id.main_role);

        Intent intent = getIntent();
        String  username = intent.getStringExtra("USERNAME");
        String password = intent.getStringExtra("PASSWORD");

        setRold(username,password);

        ProductRepository productRepository = new ProductRepository(this);
        List<Product> products = new ArrayList<>();
        products = productRepository.initializeProducts(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        ProductAdapter adapter = new ProductAdapter(this, products);
        recyclerView.setAdapter(adapter);

        main_shopCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CartActivity.class);
                intent1.putExtra("ROLE",roleSata);
                intent1.putExtra("CODE", code);
                startActivity(intent1);
            }
        });
    }

    void setRold(String username , String password){

        List<User> users = new ArrayList<>();

        users = UserData.getInstance(this).userDAO().getUser(username,password);


        if (users != null && !users.isEmpty()) {
            roleView.setText(users.get(0).getRole()+"   Code:"+users.get(0).getId());
            roleSata = users.get(0).getRole()+"Code:"+users.get(0).getId();
            code = users.get(0).getId();
        } else {
            roleView.setText(roleSata);

        }
    }
}