package tlu.cse.android.ht63.coffee.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tlu.cse.android.ht63.coffee.DAO.UserDAO;
import tlu.cse.android.ht63.coffee.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserData extends RoomDatabase {

    private static final String DATABASE_NAME = "users";

    private static UserData instance;

    public abstract UserDAO userDAO();
    public static synchronized UserData getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserData.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
