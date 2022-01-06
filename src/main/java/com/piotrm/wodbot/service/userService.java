package com.piotrm.wodbot.service;

import com.piotrm.wodbot.dao.UserRepository;
import com.piotrm.wodbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {

    @Autowired
    private UserRepository userRepository;

    public void saveNewUser(String email, String username, String password){
        if(!userRepository.findUserByEmail().isPresent()){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setLanguage("ENG");
            userRepository.save(user);
        }
    }
}
