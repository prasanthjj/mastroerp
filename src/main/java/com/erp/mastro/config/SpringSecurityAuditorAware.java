package com.erp.mastro.config;

import com.erp.mastro.controller.UserController;
import com.erp.mastro.dto.CurrentUserDetails;
import com.erp.mastro.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String>, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserController userController;

    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication != null) {
            user = ((CurrentUserDetails) authentication.getPrincipal()).getUser();
        }

        return Optional.ofNullable(user.getUserName());
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}
