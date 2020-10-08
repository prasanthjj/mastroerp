package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.User;
import com.erp.mastro.exception.InvalidTokenException;
import com.erp.mastro.exception.MastroEntityException;
import com.erp.mastro.exception.TokenExpiredException;
import com.erp.mastro.model.request.UserModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;


public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void saveOrUpdateUser(UserModel userModel, HttpServletRequest request) throws MastroEntityException;

    void sendResetPasswordEmail(String email, String appUrl, Locale locale) throws MastroEntityException;

    void enableUser(User user);

    void saveChangedPassword(User user, String password);

    void validatePasswordResetToken(Long id, String token, HttpServletRequest request) throws TokenExpiredException, InvalidTokenException;

    void activateOrDeactivateUser(Long id);

    boolean isEmailCorrect(String email) throws MastroEntityException;

    void saveCurrentBranch(Long branchId, User userDetails);

    void deleteUser(Long id);

    User findByEmail(String email);

    public void deleteUserDetails(Long id);

    public void savUser(User User);

}
