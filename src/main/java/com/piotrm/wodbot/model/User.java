package com.piotrm.wodbot.model;

import com.piotrm.wodbot.validation.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ValidEmail
    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(name = "username", unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "locale", nullable = false, length = 20)
    private Locale locale;
}