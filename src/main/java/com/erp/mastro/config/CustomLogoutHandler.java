package com.erp.mastro.config;

import com.erp.mastro.dto.CurrentUserDetails;
import com.erp.mastro.entities.User;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            CurrentUserDetails currentUser = (CurrentUserDetails) authentication.getPrincipal();
            User user = currentUser.getUser();
            user.setLoggedIn(false);
            userService.savUser(user);
        }
        session.invalidate();

    }
}

