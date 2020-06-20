package com.erp.mastro.controller;

import com.erp.mastro.entities.Roles;
import com.erp.mastro.entities.User;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "views/login";
    }
    @RequestMapping("/home")
    public String home(Map<String, Object> model) {
        return "views/hsn_master";
    }

    @RequestMapping("/login")
    public String login(Map<String, Object> model) {
        return "views/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @PostMapping(value = "/register")
    public String register() {

        User user = new User();
        user.setUserName("ranjit");
        user.setEmail("ranjit@halo.ae");
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        user.setEnabled(true);

        Set<Roles> rolesSet = new HashSet();
        Roles roles = new Roles();
        roles.setRole("ROLE_ADMIN");
        rolesSet.add(roles);

        user.setRoles(rolesSet);
        userService.saveOrUpdateUser(user);

        return "login";
    }
}
