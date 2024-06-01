package tlu.cse.android.ht63.coffee.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tlu.cse.android.ht63.coffee.models.Product;


@Dao
public interface ProductDAO {

    @Insert
    void insertProduct(Product product);

    @Insert
    void insertProducts(List<Product> products);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM product")
    List<Product> getListProduct();
}
