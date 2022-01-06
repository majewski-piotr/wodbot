package com.piotrm.wodbot.dao;

import com.piotrm.wodbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByEmail(String email);
}
