package com.erp.mastro.controller;

//import com.erp.mastro.common.MailUtils;
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
import java.util.stream.Collectors;

@Controller
public class UserController {

    /*@Autowired
    private MailUtils mailUtils;*/

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

/*
        mailUtils.sendSimpleMessage("gloridageorge@gmail.com","test mail","testing with utility removed impl");
*/


        try {
           /*List<User> userList = new ArrayList<>();
            userList = userService.getAllUsers();*/
          /*  for (User user : userService.getAllUsers()) {
               *//* if (!user.isEnabled()) {
                    userList.add(user);
                }*//*
              *//*  if (user.isEnabled()) {
                    userList.add(user);
                }
*//*
            }*/

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
            List<User> userList = userService.getAllUsers().stream()
                    .filter(userData -> (null != userData))
                    .sorted(Comparator.comparing(
                            User::getId).reversed())
                    .collect(Collectors.toList());

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

/*
    @PostMapping(value = "/addEmployee")
*/
    /*public void  addEmployee(){
        Employee employee = new Employee();
        employee.setEmail("ranjit@mastro.com");
        employee.setDepartment("HR");
        employee.setFirstName("Ranjit");
        employee.setContactNumber("7736212827");
        Employee employee1 = new Employee();
        employee1.setEmail("DDRR@mastro.com");
        employee1.setDepartment("HR");
        employee1.setFirstName("Ranjit");
        employee1.setContactNumber("7736212827");

        employeeService.saveOrUpdateEmployee(employee1);

        employeeService.saveOrUpdateEmployee(employee);
    }*/

    @PostMapping(value = "/register")
    public void register() {

        User user = new User();
        user.setUserName("Ranjit");
        user.setEmail("ranjit@halo.ae");
        user.setPassword(bCryptPasswordEncoder.encode("ranjit"));
        user.setEnabled(true);

        Set<Roles> rolesSet = new HashSet();
        Roles roles = new Roles();
        roles.setRoleName("ROLE_ADMIN");
        rolesSet.add(roles);

        user.setRoles(rolesSet);
        userService.savUser(user);
    }

    @PostMapping(value = "/admin/registerUser")
    public String register(@ModelAttribute ("addUserForm") @Valid UserModel userModel, HttpServletRequest request, Model model) {

        userService.saveOrUpdateUser(userModel,request);
         /*List<User> userList = userService.getAllUsers().stream()
                .filter(userData -> (null != branchData))
                .filter(branchData -> (1 != branchData.geUserDeleteStatus()))
                .sorted(Comparator.comparing(
                        Brand::getId).reversed())
                .collect(Collectors.toList());
*/


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

        Set<UserModel.UserModelEdit> userModelEdits = new HashSet<>();
         for (Roles roles : userDetails.getRoles() ){
                UserModel.UserModelEdit userEditModel = new  UserModel.UserModelEdit();
                userEditModel.setRole(roles.getRoleName());
                userEditModel.setId(roles.getId());
                userModelEdits.add(userEditModel);
        }
        List<Roles> rolesList = new ArrayList<>();
        for (Roles roles : rolesService.getAllRoles()) {
            if (roles.getRolesDeleteStatus() != 1) {
                rolesList.add(roles);
            }
        }
        //full roles
        Set<UserModel.UserModelEdit> rolemodelEdits = new HashSet<>();
       for (Roles roles : rolesList ){
           UserModel.UserModelEdit rolemodelEdit = new  UserModel.UserModelEdit();
           rolemodelEdit.setRole(roles.getRoleName());
           rolemodelEdit.setId(roles.getId());
           rolemodelEdits.add(rolemodelEdit);
        }


            List<Branch> branchList = new ArrayList<>();
        for (Branch branch : branchService.getAllBranch()) {
            if (branch.getBranchDeleteStatus() != 1) {
                branchList.add(branch);
            }
        }
        Set<UserModel.UserModelBranchEdit> branchmodelEdits = new HashSet<>();
        for (Branch branch : branchList){
            UserModel.UserModelBranchEdit branchmodelEdit = new  UserModel.UserModelBranchEdit();
            branchmodelEdit.setBranchname(branch.getBranchName());
            branchmodelEdit.setId(branch.getId());
            branchmodelEdits.add(branchmodelEdit);
        }

        List<UserModel.UserModelBranchEdit> userModelBranchEdits = new ArrayList<>();
        for (Branch branch : userDetails.getBranch() ){
            UserModel.UserModelBranchEdit editBranch = new UserModel.UserModelBranchEdit();
            editBranch.setBranchname(branch.getBranchName());
            editBranch.setId(branch.getId());
            userModelBranchEdits.add(editBranch);
        }

 /*       Set<UserModel.UserModelEdit> union = new HashSet<UserModel.UserModelEdit>(rolemodelEdits);
        union.addAll(userModelEdits);
// Prepare an intersection
        Set<UserModel.UserModelEdit> intersection = new HashSet<UserModel.UserModelEdit>(rolemodelEdits);
        intersection.removeAll(userModelEdits);
// Subtract the intersection from the union
        union.removeAll(intersection);
// Print the result*/
        return new GenericResponse(true,"get User details")
                .setProperty("userId",userDetails.getId())
                .setProperty("email",userDetails.getEmail())
                .setProperty("roles",userModelEdits)
                .setProperty("fullroles",rolemodelEdits)
                .setProperty("fullbranch",branchmodelEdits)
                .setProperty("branch",userModelBranchEdits);
    }

    @GetMapping("/admin/getActivateOrDeactivateUser")
    @ResponseBody
    public GenericResponse getUserActivate(Model model, HttpServletRequest request, @RequestParam("userId") Long userId) {
         User userDetails = userService.getUserById(userId);
        userService.activateOrDeactivateUser(userId);
      //  boolean isEnabled = userDetails.isEnabled();
        return new GenericResponse(true,"get User details")
                .setProperty("userId",userDetails.getId());
               // .setProperty("status",isEnabled);
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
