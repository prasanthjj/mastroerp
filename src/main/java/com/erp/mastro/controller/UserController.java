package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.*;
import com.erp.mastro.model.request.UserModel;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.EmployeeService;
import com.erp.mastro.service.interfaces.RolesService;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    BranchService branchService;

    @Autowired
    RolesService rolesService;

    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "views/login";
    }

    @RequestMapping("/home")
    public String home(Map<String, Object> model) {
        return "views/dashboard";
    }

    @RequestMapping("/login")
    public String login(Map<String, Object> model) {
        return "views/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

    @RequestMapping("/admin/addUser")
    public String addUser(Model model) {

        try {
            List<User> userList = new ArrayList<>();
            for (User user : userService.getAllUsers()) {
                if (user.getUserDeleteStatus() != 1) {
                    userList.add(user);
                }
            }
            List<Roles> rolesList = new ArrayList<>();
            for (Roles roles : rolesService.getAllRoles()) {
                if (roles.getRolesDeleteStatus() != 1) {
                    rolesList.add(roles);
                }
            }
            List<Branch> branchList = new ArrayList<>();
            for (Branch branch : branchService.getAllBranch()) {
                if(branch.getBranchDeleteStatus() != 1) {
                    branchList.add(branch);
                }
            }
            model.addAttribute("addUserForm", new UserModel());
            model.addAttribute("adminModule", "adminModule");
            model.addAttribute("userTab", "user");
            model.addAttribute("usersList",userList);
            model.addAttribute("roleList",rolesList);
            model.addAttribute("branchList",branchList);
            return "views/user_master";
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/register")
    public String register() {

        User user = new User();
        user.setUserName("pj");
        user.setEmail("prasanth@gmail.com");
        user.setPassword(bCryptPasswordEncoder.encode("prasanth"));
        user.setEnabled(true);

        Set<Roles> rolesSet = new HashSet();
        Roles roles = new Roles();
        roles.setRoleName("ROLE_ADMIN");
        rolesSet.add(roles);

        user.setRoles(rolesSet);
        userService.savUser(user);
        return "login";
    }

    @PostMapping(value = "/admin/registerUser")
    public String register(@ModelAttribute ("addUserForm") @Valid UserModel userModel, HttpServletRequest request, Model model) {
        userService.saveOrUpdateUser(userModel,request);


        return "redirect:/admin/addUser";
    }

    @RequestMapping(value = "/admin/employeeAutoComplete")
    @ResponseBody
    public GenericResponse EmployeeAutoComplete() {
        List<String> emails = new ArrayList<String>();
        List<Employee> employees = employeeService.getAllEmployees();
        for(Employee employee : employees) {
            String email = employee.getEmail();
            emails.add(email);
        }
        return new GenericResponse(true, "get email details")
                .setProperty("value", emails);
    }

    @GetMapping("/admin/getUserForEdit")
    @ResponseBody
    public GenericResponse getRoleForEdit(Model model, HttpServletRequest request, @RequestParam("userId") Long userId) {
         User userDetails = userService.getUserById(userId);

        List<UserModel.UserModelEdit> userModelEdits = new ArrayList<>();
         for (Roles roles : userDetails.getRoles() ){
                UserModel.UserModelEdit userEditModel = new  UserModel.UserModelEdit();
                userEditModel.setRole(roles.getRoleName());
                userEditModel.setId(roles.getId());
                userModelEdits.add(userEditModel);
        }

        List<UserModel.UserModelBranchEdit> userModelBranchEdits = new ArrayList<>();
        for (Branch branch : userDetails.getBranch() ){
            UserModel.UserModelBranchEdit editBranch = new UserModel.UserModelBranchEdit();
            editBranch.setBranchname(branch.getBranchName());
            editBranch.setId(branch.getId());
            userModelBranchEdits.add(editBranch);
        }
        return new GenericResponse(true,"get User details")
                .setProperty("userId",userDetails.getId())
                .setProperty("email",userDetails.getEmail())
                .setProperty("roles",userModelEdits)
                .setProperty("branch",userModelBranchEdits);
    }

    @PostMapping("/admin/deleteUserDetails")
    @ResponseBody
    public GenericResponse deleteRoles(Model model, HttpServletRequest request, @RequestParam("userId") Long userId) {

        try {
            userService.deleteUserDetails(userId);
            return new GenericResponse(true, "delete User Details");
        } catch (Exception e) {
            return new GenericResponse(false, e.getMessage());
        }
    }

}
