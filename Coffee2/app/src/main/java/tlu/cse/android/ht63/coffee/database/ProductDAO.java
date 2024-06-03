package tlu.cse.android.ht63.coffee.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import tlu.cse.android.ht63.coffee.model.Product;

import java.util.List;

@Dao
public interface ProductDAO {
    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM product")
    List<Product> getListProduct();
}
