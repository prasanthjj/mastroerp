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
import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private Map<String, Object> dataMap = new HashMap<>(5);
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

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public Date getCurrentLoginDate(Long id) {
        User user = userRepository.findById(id).get();
        user.setLastLogin(user.getCurrentLogin());
        user.setLoggedIn(true);
        user.setCurrentLogin(new Date());
        userRepository.save(user);
        return user.getCurrentLogin();
    }

    public Date getCurrentLoginDate(User user) {

        user.setLastLogin(user.getCurrentLogin());
        user.setLoggedIn(true);
        user.setCurrentLogin(MastroApplicationUtils.converttoTimestamp(LocalDateTime.now()));
        userRepository.save(user);
        return user.getCurrentLogin();
    }

}

