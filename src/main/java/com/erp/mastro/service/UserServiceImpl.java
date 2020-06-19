package com.erp.mastro.service;

import com.erp.mastro.entities.User;
import com.erp.mastro.repository.UserRepository;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdateUser(User User) {
        userRepository.save(User);
    }

    @Override
    public void deleteUser(Long id) {

    }
}
