package tlu.cse.android.ht63.coffee.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import tlu.cse.android.ht63.coffee.model.User;
import tlu.cse.android.ht63.coffee.utils.Converters;

@Database(entities = {User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class UserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "user.db";
    private static volatile UserDatabase INSTANCE;

    public abstract UserDAO userDAO();

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UserDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

