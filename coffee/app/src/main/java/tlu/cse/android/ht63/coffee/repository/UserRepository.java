package tlu.cse.android.ht63.coffee.repository;


import android.content.Context;



import java.util.ArrayList;
import java.util.List;

import tlu.cse.android.ht63.coffee.data.UserData;
import tlu.cse.android.ht63.coffee.models.User;

public class UserRepository {
    private static List<User> users = new ArrayList<>();

    Context context;
    public UserRepository(Context context) {
        this.context = context;
    }

    public void addUser(User user){
        UserData.getInstance(context).userDAO().insertUser(user);
    }
    public boolean login(String userName , String passWord){
        users = UserData.getInstance(context).userDAO().login(userName,passWord);
        if(users != null && !users.isEmpty()){
            return true;
        }
        return false;
    }

}
