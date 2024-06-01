package tlu.cse.android.ht63.coffee.data;


import android.content.Context;

import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import tlu.cse.android.ht63.coffee.DAO.ProductDAO;
import tlu.cse.android.ht63.coffee.models.Product;


@Database(entities = {Product.class}, version = 1)
public abstract class ProductData extends RoomDatabase {
    private static final String DATABASE_NAME = "product";

    private static ProductData instance;

    public abstract ProductDAO productDAO();
    public static synchronized ProductData getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ProductData.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
