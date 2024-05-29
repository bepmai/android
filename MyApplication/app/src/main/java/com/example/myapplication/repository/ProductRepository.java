package com.example.myapplication.repository;

import android.content.Context;

import com.example.myapplication.database.ProductDatabase;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static ArrayList<Product> productList = new ArrayList<>();

    public ProductRepository(ArrayList<Product> lst, Context context) {
        for (Product p: lst){
            ProductDatabase.getInstance(context).productDAO().insertProduct(p);
        }
    }

    public ProductRepository(Context context) {
        List<Product> lst = ProductDatabase.getInstance(context).productDAO().getListProduct();
        for (Product p: lst){
            this.productList.add(p);
        }
    }

    public ProductRepository(){

    }

    public static ArrayList<Product> getProductList(Context context) {
        return (ArrayList<Product>) ProductDatabase.getInstance(context).productDAO().getListProduct();
    }

    public static void setProductList(ArrayList<Product> productList) {
        ProductRepository.productList = productList;
    }

    public void addProduct(Product p,Context context){
        this.productList.add(p);
        ProductDatabase.getInstance(context).productDAO().insertProduct(p);
    }

    public Product getProduct(Integer id){
        Product result;
        for ( Product p : productList) {
            if (id == p.getId())
                return p;
        }
        return  null;
    }
}
