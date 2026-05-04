package org.example.ecommercewebsite.Service;

import org.example.ecommercewebsite.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    ArrayList<User> users = new ArrayList<>();

    public Boolean addUser(User user){
        for(User user1: users){
            if(user1.getId().equalsIgnoreCase(user.getId())){
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public Boolean updateUser(String id, User user){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId().equalsIgnoreCase(id)){
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    public Boolean deleteUser(String id){
        for(User user: users){
            if(user.getId().equalsIgnoreCase(id)){
                users.remove(user);
                return true;
            }
        }
        return false;
    }
}
