package com.hao.demo.service;

import com.hao.demo.bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class UserService {
    final AtomicLong counter = new AtomicLong();
    private Map<Long, User> users;

    public UserService(){
        setupTestUsers();
    }

    public User getById(long id){
        if(999 == id){
            throw new RuntimeException(); //Mock a random exception, CustomizedResponseEntityExceptionHandler shall handles it
        }
        return users.get(id);
    }

    public List<User> getUsers(){
        return new ArrayList<User>(users.values());
    }

    public void createUser(User user){
        users.put(user.getId(), user);
    }

    public boolean exists(User user){
        return null != users.get(user.getId());
    }

    public void updateUser(User user){
        users.put(user.getId(), user);
    }

    public void deleteUser(User user){
        users.remove(user.getId());
    }

    private void setupTestUsers(){
        users = new HashMap<>();
        User u1 = new User();
        u1.setId(counter.incrementAndGet());
        u1.setFirstName("Deng");
        u1.setLastName("Hao");

        User u2 = new User();
        u2.setId(counter.incrementAndGet());
        u2.setFirstName("Pan");
        u2.setLastName("Peter");

        User u3 = new User();
        u3.setId(counter.incrementAndGet());
        u3.setFirstName("Jan");
        u3.setLastName("Smith");

        users.put(u1.getId(), u1);
        users.put(u2.getId(), u2);
        users.put(u3.getId(), u3);
    }
}