package tlu.cse.android.ht63.coffee.adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tlu.cse.android.ht63.coffee.CartActivity;
import tlu.cse.android.ht63.coffee.R;
import tlu.cse.android.ht63.coffee.models.Cart;
import tlu.cse.android.ht63.coffee.models.Product;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Cart cart;
    private Context context;

    public CartAdapter(Cart cart, Context context) {
        this.cart = cart;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = cart.getProducts().get(position);
        holder.nameProduct.setText(product.getName());
        holder.price.setText(String.format("%.2f", product.getPrice()));
        holder.imageView.setImageURI(Uri.parse(product.getUriImage()));
        holder.cart_amount.setText(String.valueOf(cart.getQuantity(product)));
        holder.art_total.setText(String.format("%.2f", cart.getTotalPrice()));

        holder.cart_plus.setOnClickListener(v -> {
            cart.addCart(product);
            holder.cart_amount.setText(String.valueOf(cart.getQuantity(product)));
            holder.art_total.setText(String.format("%.2f", cart.getLinePrice(product)));
            ((CartActivity) context).updateTotalPrice();

        });

        holder.cart_minus.setOnClickListener(v -> {
            cart.removeCart(product);
            holder.cart_amount.setText(String.valueOf(cart.getQuantity(product)));
            holder.art_total.setText(String.format("%.2f", cart.getLinePrice(product)));
            ((CartActivity) context).updateTotalPrice();
        });

    }

    @Override
    public int getItemCount() {
        return cart.getProducts().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameProduct,price,cart_plus,cart_amount,cart_minus,art_total;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameProduct = itemView.findViewById(R.id.cart_nameProduct);
            price = itemView.findViewById(R.id.cart_price);
            cart_plus = itemView.findViewById(R.id.cart_plus);
            cart_amount = itemView.findViewById(R.id.amount);
            cart_minus = itemView.findViewById(R.id.cart_minus);
            art_total = itemView.findViewById(R.id.cart_total);
            imageView = itemView.findViewById(R.id.cart_image);


        }
    }
}
