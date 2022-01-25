package com.piotrm.wodbot.service;

import com.piotrm.wodbot.dao.UserRepository;
import com.piotrm.wodbot.model.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisTemplate<Long, Long> redisTemplate;

    private UserService testService;
    private User testUser;

    @BeforeEach
    void setUp(){
        this.testService = new UserService(userRepository,passwordEncoder,redisTemplate);
        this.testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("1234");
        testUser.setId(1L);
        testUser.setEmail("pawelek11@wp.pl");
        testUser.setLocale(new Locale.Builder().setLanguage("en").setRegion("EN").build());
    }

    @Test
    void saveNewUserCallsRepository() {
        when(userRepository.findUserByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(testUser);

        testService.saveNewUser(testUser.getEmail(),testUser.getUsername(),testUser.getPassword());
        Mockito.verify(userRepository,Mockito.times(1)).save(any(User.class));
    }

    @Test
    void getUserIdFromDiscordUser() {
    }

    @Test
    void loginUser() {
    }

    @Test
    void isLogged() {
    }

    @Test
    void getLocaleFromCache() {
    }

    @Test
    void changeLocale() {
    }
}