package com.erp.mastro.service;

import com.erp.mastro.common.MailUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.*;
import com.erp.mastro.model.request.UserModel;
import com.erp.mastro.repository.EmployeeRepository;
import com.erp.mastro.repository.UserRepository;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.ModuleService;
import com.erp.mastro.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public void saveOrUpdateUser(UserModel userModel, HttpServletRequest request) {

        /* mailUtils.sendSimpleMessage("gloridageorge@gmail.com","test mail","Testing mail for mail utility"); */

        User user = userRepository.findByEmail(userModel.getEmail());
        if (user == null) {
            MastroLogUtils.info(UserService.class, "Going to Add User {}" + userModel.toString());
            Employee employee = employeeRepository.findByEmail(userModel.getEmail());
            user = new User();
            user.setUserName(employee.getFirstName());
            user.setEmployee(employee);
            user.setEmail(userModel.getEmail());
            user.setEnabled(true);

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
        MastroLogUtils.info(UserService.class, "Save or Update " + userModel.getEmail() + " successfully.");
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
