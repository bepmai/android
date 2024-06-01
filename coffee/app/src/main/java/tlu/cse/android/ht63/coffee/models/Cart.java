package tlu.cse.android.ht63.coffee.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private static float totalPrice;
    private static Map<Product, Integer> productMap = new HashMap<>();

    public Cart() {
    }

    public static float getTotalPrice() {
        return totalPrice;
    }

    public static void setTotalPrice(float totalPrice) {
        Cart.totalPrice = totalPrice;
    }

    public void addCart(Product product) {
        int quantity = productMap.getOrDefault(product, 0);
        if (quantity < 10) {
            productMap.put(product, quantity + 1);
            totalPrice += product.getPrice();
        }
    }

    public void removeCart(Product product) {
        int quantity = productMap.getOrDefault(product, 0);
        if (quantity > 0) {
            productMap.put(product, quantity - 1);
            totalPrice -= product.getPrice();
        }
    }

    public int getQuantity(Product product) {
        return productMap.getOrDefault(product, 0);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(productMap.keySet());
    }

    public float getLinePrice (Product p){
        return p.getPrice() * productMap.getOrDefault(p, 0);
    }
}
