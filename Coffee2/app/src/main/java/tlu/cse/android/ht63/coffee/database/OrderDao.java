package tlu.cse.android.ht63.coffee.database;

import androidx.room.Dao;
import androidx.room.Insert;

import tlu.cse.android.ht63.coffee.model.Order;

@Dao
public interface OrderDao {
    @Insert
    long insert(Order order);
}

