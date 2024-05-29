package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.CartsAdapter;
import com.example.myapplication.model.Cart;

public class CartsActivity extends AppCompatActivity {
    private RecyclerView rvProduct;
    private TextView tvTotal;
    private Cart cart = new Cart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTotal = findViewById(R.id.tvTotal);
        rvProduct = findViewById(R.id.rvproduct);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        rvProduct.setLayoutManager(mLayoutManager);

        CartsAdapter rvAdapter  = new CartsAdapter(this, this.cart);
        int count = rvAdapter.getItemCount();
        rvProduct.setAdapter(rvAdapter);
        tvTotal.setText(""+this.cart.getTotalPrice());
    }
    public void updateData(){
        tvTotal.setText("" + this.cart.getTotalPrice());
    }
}