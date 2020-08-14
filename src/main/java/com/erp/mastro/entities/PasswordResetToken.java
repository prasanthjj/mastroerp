package com.erp.mastro.entities;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "user_pwd_reset_token")
public class PasswordResetToken {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetToken.class);
    private static final int EXPIRATION = 3 * 24 * 60 * 60 * 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.creationDate = new Date();
    }

    public PasswordResetToken() {
        super();
    }

    public User getUser() {
        return user;
    }

    public boolean isValidUserToken(Long id) {
        return user.getId().equals(id);
    }

    public boolean isExpired() {
        return Duration.between(this.creationDate.toInstant().plusMillis(EXPIRATION), Instant.now()).isNegative() ? false : true;
    }

}
