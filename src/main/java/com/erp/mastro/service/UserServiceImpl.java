package com.erp.mastro.service;

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
        /* if (userModel.getId() == null) {*/

        User user = userRepository.findByEmail(userModel.getEmail());
        Iterable<Roles> rolesIterable = rolesRepository.findAll();
        if (user == null) {
            user = new User();
            System.out.println("USER:" + user.getEmail());
            System.out.println("inside the employee:" + user.getEmail());

            Employee employee = employeeRepository.findByEmail(userModel.getEmail());
            user.setUserName(employee.getFirstName());
            user.setEmployee(employee);
            user.setEmail(userModel.getEmail());
            user.setEnabled(true);

            Set<Roles> roles = userModel.getRoles();
            user.setRoles(roles);

           /* Set<Branch> branches = userModel.getBranch();
            user.setBranch(branches);*/

            userRepository.save(user);
        }
        /*else {
            if  (user.getEnabled()==false){
                user.setEnabled(true);
                userRepository.save(user);
                System.out.println("enabled value:"+(user.getEnabled());

            }
        }*/ /*else {
                      throw new UserAlreadyExistsException();
                      This email alredy exists

        }
*/
        // }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteUserDetails(Long id) {

        User user = getUserById(id);
        user.setEnabled(false);
        userRepository.delete(user);

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
