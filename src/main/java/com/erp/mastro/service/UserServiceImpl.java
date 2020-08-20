package com.erp.mastro.service;

import com.erp.mastro.common.MailUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.common.UrlUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.InvalidTokenException;
import com.erp.mastro.exception.MastroEntityException;
import com.erp.mastro.exception.TokenExpiredException;
import com.erp.mastro.model.request.UserModel;
import com.erp.mastro.repository.EmployeeRepository;
import com.erp.mastro.repository.PasswordResetTokenRepository;
import com.erp.mastro.repository.UserRepository;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.ModuleService;
import com.erp.mastro.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

/**
 * Service class for User
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BranchService branchService;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    /**
     * method to get all Users
     *
     * @return users list
     */
    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<User>();
        userRepository.findAll().forEach(users -> usersList.add(users));
        return usersList;
    }

    /**
     * method to get User according to id
     *
     * @param id
     * @return User
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * Save or edit User details
     *
     * @param userModel
     * @param request
     */
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateUser(UserModel userModel, HttpServletRequest request) throws MastroEntityException {

        User user = userRepository.findByEmail(userModel.getEmail());
        if (user == null) {
            user = new User();
            MastroLogUtils.info(UserService.class, "Going to Add User {}" + userModel.toString());
            Employee employee = employeeRepository.findByEmail(userModel.getEmail());
            System.out.println("employee :: " + employee);
            user.setUserName(employee.getEmail());
            user.setEmployee(employee);
            user.setEmail(userModel.getEmail());
            user.setEnabled(false);

            Set<Roles> roles = userModel.getRoles();
            user.setRoles(roles);
            Set<Branch> branches = userModel.getBranch();
            user.setBranch(branches);
        } else if (!user.isEnabled()) {
            MastroLogUtils.info(UserService.class, "Going to Enable User {}" + userModel.toString());
            user.setEnabled(userModel.isEnabled());
        } else {
            MastroLogUtils.info(UserService.class, "Going to Update User {}" + userModel.toString());
            user.setUserName(userModel.getUserName());

            Set<Roles> roles = userModel.getRoles();
            user.setRoles(roles);
            Set<Branch> branches = userModel.getBranch();
            user.setBranch(branches);
        }
        userRepository.save(user);
        String email = userModel.getEmail();
        sendResetPasswordEmail(email, UrlUtils.getAppurl(request), request.getLocale());
        MastroLogUtils.info(UserService.class, "Save or Update " + userModel.getEmail() + " successfully.");
    }

    /**
     * Password Reset Method
     *
     * @param email
     * @param appUrl
     * @param locale
     * @throws MastroEntityException
     */
    public void sendResetPasswordEmail(String email, String appUrl, Locale locale) throws MastroEntityException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new MastroEntityException();
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        sentResetPasswordTokenEmail(appUrl, locale, token, user);
    }

    /**
     * Token Reset method
     *
     * @param user
     * @param token
     */

    private void createPasswordResetTokenForUser(User user, String token) {
        MastroLogUtils.info(UserServiceImpl.class, "Going to create token for password reset : {}");
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    /**
     * Method to sent email with token
     *
     * @param contextPath
     * @param locale
     * @param token
     * @param user
     * @return
     */
    private boolean sentResetPasswordTokenEmail(String contextPath, Locale locale, String token, User user) {
        MastroLogUtils.info(UserServiceImpl.class, "Going to send email : {}");
        String url = contextPath + "changePassword?id=" + user.getId() + "&token=" + token;
        Map emailMap = new HashMap();
        emailMap.put("url", url);
        try {
            if (user.isEnabled()) {
                mailUtils.sendMessageUsingThymeleafTemplate(user.getEmail(), "Welcome Email From Mastro Metals", emailMap, Constants.TEMPLATE_FORGOTPASSWORD);
            } else {
                mailUtils.sendMessageUsingThymeleafTemplate(user.getEmail(), "Welcome Email From Mastro Metals", emailMap, Constants.TEMPLATE_WELCOMEMAIL);
            }
            MastroLogUtils.info(UserServiceImpl.class, "Email sent to the user : {}");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return true;

    }

    /**
     * Method to check token
     *
     * @param id
     * @param token
     * @param request
     * @throws TokenExpiredException
     * @throws InvalidTokenException
     */

    public void validatePasswordResetToken(Long id, String token, HttpServletRequest request) throws TokenExpiredException, InvalidTokenException {
        Optional<PasswordResetToken> passTokenOpt = passwordTokenRepository.findByToken(token);
        PasswordResetToken passToken = passTokenOpt.isPresent() ? passTokenOpt.get() : null;

        if ((passToken == null) || !passToken.isValidUserToken(id)) {
            throw new InvalidTokenException();
        } else if (passToken.isExpired()) {
            throw new TokenExpiredException();
        } else {
            User user = passToken.getUser();
            Authentication auth = new UsernamePasswordAuthenticationToken(user,
                    null,
                    Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        }
    }

    /**
     * Enable user
     *
     * @param user
     */
    public void enableUser(User user) {
        MastroLogUtils.info(UserServiceImpl.class, "Going to enable user : {}");
        user.setEnabled(true);
        userRepository.save(user);
    }

    /**
     * Method to check email
     *
     * @param email
     * @return
     * @throws MastroEntityException
     */
    @Transactional(rollbackOn = {Exception.class})
    public boolean isEmailCorrect(String email) throws MastroEntityException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to save password
     *
     * @param user
     * @param password
     */
    public void saveChangedPassword(User user, String password) {
        MastroLogUtils.info(UserServiceImpl.class, "Going to encrypt passwod : {}");
        user.setPassword(password);
        user.encryptPassword();
        userRepository.save(user);
        enableUser(user);

    }

    /**
     * Save Current Branch selected
     *
     * @param branchId
     * @param userDetails
     */
    @Override
    public void saveCurrentBranch(Long branchId, User userDetails) {
        if (branchId != null) {
            Branch branch = branchService.getBranchById(branchId);
            UserSelectedBranch userSelectedBranch = new UserSelectedBranch();
            userSelectedBranch.setCurrentBranch(branch);
            userDetails.setUserSelectedBranch(userSelectedBranch);
            userRepository.save(userDetails);
            MastroLogUtils.info(UserService.class, "Updated current branch successfully.");
        }
    }

    /**
     * Activate or Deactivate User
     *
     * @param id
     */
    @Override
    public void activateOrDeactivateUser(Long id) {
        if (id != null) {
            User user = getUserById(id);
            if (user.isEnabled()) {
                user.setEnabled(false);
            } else {
                user.setEnabled(true);
            }
            userRepository.save(user);
            MastroLogUtils.info(UserService.class, "Activate or Deactivate " + user.getEmail() + " successfully.");
        } else {
            MastroLogUtils.info(UserService.class, "User Id null");
        }
    }

    /**
     * Delete User
     *
     * @param id
     */
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        MastroLogUtils.info(ModuleService.class, "Deleted successfully.");
    }

    /**
     * Delete User Details
     *
     * @param id
     */
    @Transactional(rollbackOn = {Exception.class})
    public void deleteUserDetails(Long id) {
        User user = getUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
        MastroLogUtils.info(ModuleService.class, "User Disabled successfully.");
    }

    /**
     * method to get User according to email id
     *
     * @param term
     */
    @Override
    public User findByEmail(String term) {
        return userRepository.findByEmail(term);
    }

    /**
     * method to save User
     *
     * @param User
     */
    @Override
    public void savUser(User User) {
        userRepository.save(User);
    }

}
