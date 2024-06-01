package tlu.cse.android.ht63.coffee.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import tlu.cse.android.ht63.coffee.models.User;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(User users);

    @Delete
    void delete(User users);

    @Query("SELECT * FROM users")
    List<User> getListUser();

    @Query("SELECT * FROM users where userName= :userName")
    List<User> checkUser(String userName);

    @Query("SELECT * FROM users where userName= :userName and passWord = :passWord")
    List<User> login(String userName,String passWord);

    @Query("SELECT * FROM users where userName= :userName and passWord = :passWord")
    List<User> getUser(String userName,String passWord);
}
