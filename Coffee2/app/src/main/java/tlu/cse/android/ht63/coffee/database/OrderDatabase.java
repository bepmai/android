package tlu.cse.android.ht63.coffee.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tlu.cse.android.ht63.coffee.model.Order;

@Database(entities = {Order.class}, version = 1)
public abstract class OrderDatabase extends RoomDatabase {
    private static volatile OrderDatabase INSTANCE;

    public abstract OrderDao orderDao();

    public static OrderDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (OrderDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    OrderDatabase.class, "orders-db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
