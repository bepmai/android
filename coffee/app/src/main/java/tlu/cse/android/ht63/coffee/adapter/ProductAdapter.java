package tlu.cse.android.ht63.coffee.adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import tlu.cse.android.ht63.coffee.R;
import tlu.cse.android.ht63.coffee.models.Cart;
import tlu.cse.android.ht63.coffee.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{


    private List<Product> productList;
    private Context context;

    public Cart cart = new Cart();
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.nameProduct.setText(product.getName());
        holder.priceProduct.setText(String.format("%.2f", product.getPrice()));
        holder.imageProduct.setImageURI(Uri.parse(product.getUriImage()));

        holder.addCart.setOnClickListener(v -> {
            cart.addCart(product);
            Toast.makeText(context,"click on item: "+ product.getName(), Toast.LENGTH_LONG).show();
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{


        ImageView imageProduct,addCart;
        TextView nameProduct,priceProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProduct = itemView.findViewById(R.id.product_image);
            nameProduct = itemView.findViewById(R.id.product_nameProduct);
            priceProduct = itemView.findViewById(R.id.product_price);
            addCart = itemView.findViewById(R.id.product_addCart);

        }
    }
}
