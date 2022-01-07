package com.piotrm.wodbot.service;

import com.piotrm.wodbot.dao.UserRepository;
import com.piotrm.wodbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<Long, Long> redisTemplate;

    public boolean saveNewUser(String email, String username, String password) {
        if (!userRepository.findUserByEmail(email).isPresent()) {
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

    public boolean loginUser(String username, String password, Optional<discord4j.core.object.entity.User> discordUser) {
        if (authenticate(username, password) && discordUser.isPresent()) {
            Long snowflake = discordUser.get().getId().asLong();
            Long userId = userRepository.findUserByUsername(username).get().getId();
            redisTemplate.opsForValue().set(snowflake, userId, Duration.of(5, ChronoUnit.HOURS));
            return true;
        }
        return false;
    }

    public boolean isLogged(discord4j.core.object.entity.User discordUser) {
        Long snowFlake = discordUser.getId().asLong();
        Long userId = -1L;
        userId = redisTemplate.opsForValue().get(snowFlake);
        if (userId != null && userId != -1 && userRepository.findById(userId).isPresent()) {
            return true;
        }
        return false;
    }

    private boolean authenticate(String username, String password) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return passwordEncoder.matches(password, user.get().getPassword());
        }
        return false;
    }
}
