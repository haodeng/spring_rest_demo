package com.hao.demo.service;

import com.hao.demo.bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserService {
    final AtomicLong counter = new AtomicLong();

    public User getById(long id){
        User u1 = new User();
        u1.setId(id);
        u1.setFirstName("Deng");
        u1.setLastName("Hao");
        return u1;
    }

    public List<User> getUsers(){

        List<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setId(counter.incrementAndGet());
        u1.setFirstName("Deng");
        u1.setLastName("Hao");

        User u2 = new User();
        u2.setId(counter.incrementAndGet());
        u2.setFirstName("Pan");
        u2.setLastName("Peter");

        users.add(u1);
        users.add(u2);
        return users;
    }

    public void createUser(User user){

    }

    public boolean exists(User user){
        return false;
    }

    public void updateUser(User user){

    }

    public void deleteUser(User user){

    }
}