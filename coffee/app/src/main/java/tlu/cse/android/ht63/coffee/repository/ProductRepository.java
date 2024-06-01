package tlu.cse.android.ht63.coffee.repository;


import android.content.Context;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tlu.cse.android.ht63.coffee.data.ProductData;
import tlu.cse.android.ht63.coffee.models.Product;

public class ProductRepository {
    private static List<Product> productList = new ArrayList<>();
    Context context;
    public ProductRepository(Context context) {
        this.context = context;

    }

    public static List<Product> getProductList() {
        return productList;
    }

    public static void setProductList(List<Product> productList) {
        ProductRepository.productList = productList;
    }


    public int countNullProducts() {
        if(productList.isEmpty())
        {
            productList = ProductData.getInstance(context).productDAO().getListProduct();
        }
        return productList.size();
    }

    public List<Product> initializeProducts(Context context){
        if(countNullProducts() == 0){
            List<Product> products = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < 20; i++) {
                String name = "Product" + i;
                float price = random.nextFloat() * 1000;
                int resID = context.getResources().getIdentifier("tt_" + i % 6, "drawable", context.getPackageName());
                String imgUri = "android.resource://" + context.getPackageName() + "/" + resID;

                products.add(new Product(name, price, imgUri));
            }
            ProductData.getInstance(context).productDAO().insertProducts(products);
        }
        return productList;
    }

}
