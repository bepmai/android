package tlu.cse.android.ht63.coffee.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import tlu.cse.android.ht63.coffee.model.User;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User authenticate(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username")
    User checkUser(String username);

    @Query("SELECT * FROM users WHERE employeename = :employeename")
    User checkUserByEmployeeID(String employeename);
}
