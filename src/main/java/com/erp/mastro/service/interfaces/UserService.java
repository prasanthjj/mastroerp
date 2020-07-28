package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.User;
import com.erp.mastro.model.request.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void saveOrUpdateUser(UserModel userModel, HttpServletRequest request);

    void activateOrDeactivateUser(Long id);

    void deleteUser(Long id);

    User findByEmail(String email);

    public void deleteUserDetails(Long id);

    public void savUser(User User);
}
