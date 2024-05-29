package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.ProductsAdapter;
import com.example.myapplication.database.ProductDatabase;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Product;
import com.example.myapplication.repository.ProductRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ProductRepository productRepository;
    RecyclerView rvProduct;
    FloatingActionButton mnu_cart;
    private Cart cart = new Cart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initData();
        productRepository = new ProductRepository(this);

        rvProduct = findViewById(R.id.rvproduct);
        mnu_cart = findViewById(R.id.mnu_cart);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvProduct.setLayoutManager(mLayoutManager);

        ProductsAdapter rvAdapter  = new ProductsAdapter(this, this.productRepository.getProductList(this));
        rvProduct.setAdapter(rvAdapter);

        mnu_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("this","cart show here");
                Intent intent = new Intent(MainActivity.this, CartsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initData(){
        List<Product> list = ProductDatabase.getInstance(this).productDAO().getListProduct();
        if (list ==null || list.isEmpty()){
            ArrayList<Product> alProduct = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Product p = new Product("ProductName" + i);
                p.setDescreption("The iPhone 15 is the latest flagship smartphone lineup from Apple, introduced in 2023."+
                        "The iPhone 15 is the latest flagship smartphone lineup from Apple, introduced in 2023.");
                p.setImage(R.drawable.ss_0);
                String random = String.format("%.2f",new Random().nextFloat() * 1000);
                random = random.replace(",", "");
                float price = Float.parseFloat(random);
                p.setPrice(price);
                alProduct.add(p);
            }
            this.productRepository = new ProductRepository(alProduct,this);
        }
    }
    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}