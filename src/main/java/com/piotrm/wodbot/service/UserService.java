package com.piotrm.wodbot.service;

import com.piotrm.wodbot.dao.UserRepository;
import com.piotrm.wodbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveNewUser(String email, String username, String password){
        if(!userRepository.findUserByEmail(email).isPresent()){
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setLanguage("ENG");
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
