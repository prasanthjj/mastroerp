package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void saveOrUpdateUser(User User);

    void deleteUser(Long id);
}
