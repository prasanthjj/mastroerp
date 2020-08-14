package com.erp.mastro.config;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.dto.CurrentUserDetails;
import com.erp.mastro.entities.User;
import com.erp.mastro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid Username or Password");
        }

        if (user.getId() != null) {
            getCurrentLoginDate(user);
        }

        return new CurrentUserDetails(user);
    }

    public Date getCurrentLoginDate(User user) {

        user.setLastLogin(user.getCurrentLogin());
        user.setLoggedIn(true);
        user.setCurrentLogin(MastroApplicationUtils.converttoTimestamp( LocalDateTime.now()));
        userRepository.save(user);
        return user.getCurrentLogin();
    }

}