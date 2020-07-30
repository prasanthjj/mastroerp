package com.erp.mastro.service;

import com.erp.mastro.common.MailUtils;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.Employee;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.entities.User;
import com.erp.mastro.model.request.UserModel;
import com.erp.mastro.repository.EmployeeRepository;
import com.erp.mastro.repository.RolesRepository;
import com.erp.mastro.repository.UserRepository;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.RolesService;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private BranchService branchService;

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<User>();
        userRepository.findAll().forEach(users -> usersList.add(users));
        return usersList;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateUser(UserModel userModel, HttpServletRequest request) {
        /*mailUtils.sendSimpleMessage("gloridageorge@gmail.com","test mail","Testing mail for mail utility");*/

        User user = userRepository.findByEmail(userModel.getEmail());
        // register New User
        if (user == null) {

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

        }
        else if(!user.isEnabled()){
            user.setEnabled(userModel.isEnabled());


        }
        else{
            user.setUserName(userModel.getUserName());
            //edit codes
            Set<Roles> roles = userModel.getRoles();
            user.setRoles(roles);

            Set<Branch> branches = userModel.getBranch();
            user.setBranch(branches);


        }
        userRepository.save(user);
    }

    @Override
    public void activateOrDeactivateUser(Long id) {
        User user = getUserById(id);
        if(user.isEnabled()) {
            user.setEnabled(false);

        } else {
            user.setEnabled(true);
        }
/*
        final Boolean status = user.isEnabled() ? "Active":"Deactive";
*/
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteUserDetails(Long id) {

        User user = getUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
        /*userRepository.delete(user);*/
    }

    @Override
    public User findByEmail(String term) {
        return null;
    }

    @Override
    public void savUser(User User) {
        userRepository.save(User);
    }
}
