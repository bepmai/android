package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CartsActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Cart;
import com.example.myapplication.model.Product;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.ViewHolder> {

    private Cart cart;
    private Context mContext;

    public CartsAdapter(Context context, Cart cart) {
        this.mContext = context;
        this.cart = cart;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Integer pid = cart.getProductByOrder(position).getId();
        final Product p = cart.productRepository.getProduct(pid);

        String sProductName = p.getName();
        Integer amount = cart.cartList.get(pid);
        holder.txtProductName.setText(sProductName);
        holder.txtPrice.setText(""+p.getPrice());
        holder.idIVSSImage.setImageResource(p.getImage());
        holder.edAmount.setText("" + amount);
        holder.tvLineTotal.setText("" + cart.getLinePrice(p));

        holder.tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addCart(p);
                Integer amount = cart.cartList.get(pid);
                holder.edAmount.setText(""+ amount );
                holder.tvLineTotal.setText("" + cart.getLinePrice(p));
                ((CartsActivity)mContext).updateData();
            }
        });
        holder.tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.removeCart(p);
                Integer amount = cart.cartList.get(pid);
                holder.edAmount.setText(""+ amount );
                holder.tvLineTotal.setText("" + cart.getLinePrice(p));
                ((CartsActivity)mContext).updateData();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart.cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtProductName;
        public TextView txtPrice;
        public TextView tvLineTotal;
        public EditText edAmount;
        public TextView tvPlus;
        public TextView tvMinus;
        public ImageView idIVSSImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtProductName = itemView.findViewById(R.id.idTVName);
            this.txtPrice = itemView.findViewById(R.id.idTVPrice);
            this.idIVSSImage = itemView.findViewById(R.id.idIVSSImage);
            this.edAmount = itemView.findViewById(R.id.amount);
            this.tvLineTotal = itemView.findViewById(R.id.tvLineTotal);
            this.tvPlus = itemView.findViewById(R.id.tvplus);
            this.tvMinus = itemView.findViewById(R.id.tvminus);
        }
    }
}
