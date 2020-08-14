package com.erp.mastro.controller;

import com.erp.mastro.common.MailUtils;
import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.dto.CurrentUserDetails;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.Employee;
import com.erp.mastro.entities.Roles;
import com.erp.mastro.entities.User;
import com.erp.mastro.exception.MastroEntityException;
import com.erp.mastro.model.request.UserModel;
import com.erp.mastro.service.interfaces.BranchService;
import com.erp.mastro.service.interfaces.EmployeeService;
import com.erp.mastro.service.interfaces.RolesService;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private MailUtils mailUtils;

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

    @Autowired
    HttpSession session;


    /**
     * Method to login
     *
     * @param model
     * @return to the login page
     */
    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        return "views/login";
    }

    /**
     * Method to login
     *
     * @param model
     * @return to the login page
     */
    @RequestMapping("/login")
    public String login(Map<String, Object> model) { return "views/login"; }

    /**
     * Method to view dashboard
     *
     * @param model
     * @return selected branch and branch list
     */
    @RequestMapping("/home")
    public String home(Model model ) {
        User userDetails = getCurrentUser();
        List<Branch> branchList = new ArrayList<>();
        String currentBranch = null;
        if (null != userDetails) {
            for (Branch branch : userDetails.getBranch()) {
                branchList.add(branch);
            }
            if (userDetails.getUserSelectedBranch() != null && userDetails.getUserSelectedBranch().getCurrentBranch() != null) {
                currentBranch = userDetails.getUserSelectedBranch().getCurrentBranch().getBranchName();
            }
        }
        Set<User> userListCount = userService.getAllUsers().stream()
                .filter(userData -> (null != userData))
                .filter(userData -> (false != userData.isEnabled()))
                .filter(userData -> (false != userData.isLoggedIn()))
                .collect(Collectors.toSet());

        session.setAttribute("ActiveUser", userListCount.size());
        session.setAttribute("lastLoginDate", userDetails.getLastLogin());
        session.setAttribute("selectedBranch", currentBranch);
        session.setAttribute("branchList", branchList);

        return "views/dashboard";
    }

    /**
     * Method to get the selected branch
     *
     * @param branchId
     * @param model
     * @return selected branch name
     */
    @PostMapping("/admin/selectBranchName")
    public String getBranchName( Model model, @Valid Long branchId) {
        Branch branch = branchService.getBranchById(branchId);
        String branchName = branch.getBranchName();
        User userDetails = getCurrentUser();
        userService.saveCurrentBranch(branchId,userDetails);
        model.addAttribute("branchName",branchName);
        return "redirect:/home";
    }

    /**
     * Method to redirect to access denied
     *
     * @return to the access-denied page
     */
    @GetMapping("/error/accessDenied")
    public String accessDenied() { return "views/error/access-denied"; }

    /**
     * Method to add new User
     *
     * @param model
     * @return added user list with branch and role list
     */
    @RequestMapping("/admin/addUser")
    public String addUser(Model model) {
        MastroLogUtils.info(UserController.class, "Going to add User :{}");
        try {

            List<String> emailList = new ArrayList<String>();
            List<Employee> employeesList = employeeService.getAllEmployees();
            for(Employee employee : employeesList) {
                String email = employee.getEmail();
                emailList.add(email);
            }

            List<Employee> employeeList = employeeService.getAllEmployees().stream()
                    .filter(employeData -> (null != employeData))
                    .filter(employeData -> (null ==  employeData.getUser()))
                    .sorted(Comparator.comparing(
                            Employee::getId).reversed())
                    .collect(Collectors.toList());

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
            model.addAttribute("employeesList",employeeList);
            model.addAttribute("roleList",rolesList);
            model.addAttribute("branchList",branchList);
            return "views/user_master";
        } catch (Exception e) {
            MastroLogUtils.error(UserController.class, "Error occured while adding user : {}");
            throw e;
        }
    }

    /**
     * Method to save User for test
     *
     * @return saved user
     */
    @PostMapping(value = "/register")
    public void register() {

        User user = new User();
        user.setUserName("soumyar@halo.ae");
        user.setEmail("soumyar@halo.ae");
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        user.setEnabled(true);

        Set<Roles> rolesSet = new HashSet();
        Roles roles = new Roles();
        roles.setRoleName("ROLE_ADMIN");
        rolesSet.add(roles);

        user.setRoles(rolesSet);
        user.setCreatedDate(MastroApplicationUtils.converttoTimestamp(LocalDateTime.now()));
        user.setCreatedBy(user.getEmail());

        userService.savUser(user);
    }

    /**
     * Method to register User
     *
     * @param model
     * @param request
     * @param userModel
     * @return saved User details
     */
    @PostMapping(value = "/admin/registerUser")
    public String register(@ModelAttribute("addUserForm") @Valid UserModel userModel, HttpServletRequest request, Model model) throws MastroEntityException {
        MastroLogUtils.info(UserController.class, "Going to save User :{}");
        try {
            userService.saveOrUpdateUser(userModel, request);
            return "redirect:/admin/addUser";
        } catch (Exception e) {
            MastroLogUtils.error(UserController.class, "Error occured while registering user : {}", e);
            throw e;
        }

    }


    /**
     * Method for autocomplete employee email address
     *
     * @return email address of te employee
     */
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

    /**
     * Method to edit User
     *
     * @param model
     * @param request
     * @param userId
     * @return edited User details
     */
    @GetMapping("/admin/getUserForEdit")
    @ResponseBody
    public GenericResponse getRoleForEdit(Model model, HttpServletRequest request, @RequestParam("userId") Long userId) {

        MastroLogUtils.info(UserController.class, "Going to edit User :{}" + userId);
        User userDetails = userService.getUserById(userId);
        Set<UserModel.UserModelEdit> userModelEdits = new HashSet<>();
         for (Roles roles : userDetails.getRoles() ){
                UserModel.UserModelEdit userEditModel = new  UserModel.UserModelEdit();
                userEditModel.setRole(roles.getRoleName());
                userEditModel.setId(roles.getId());
                userModelEdits.add(userEditModel);
        }
        Set<Roles> rolesList = new HashSet<>();
        for (Roles roles : rolesService.getAllRoles()) {
            if (roles.getRolesDeleteStatus() != 1) {
                rolesList.add(roles);
            }
        }
        Set<UserModel.UserModelEdit> rolemodelEdits = new HashSet<>();
        for (Roles roles : rolesList ){
            UserModel.UserModelEdit rolemodelEdit = new  UserModel.UserModelEdit();
            rolemodelEdit.setRole(roles.getRoleName());
            rolemodelEdit.setId(roles.getId());
            rolemodelEdits.add(rolemodelEdit);
        }
        Set<Branch> branchList = new HashSet<>();
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
        Set<UserModel.UserModelBranchEdit> userModelBranchEdits = new HashSet<>();
        for (Branch branch : userDetails.getBranch() ){
            UserModel.UserModelBranchEdit editBranch = new UserModel.UserModelBranchEdit();
            editBranch.setBranchname(branch.getBranchName());
            editBranch.setId(branch.getId());
            userModelBranchEdits.add(editBranch);
        }

        Iterator<UserModel.UserModelEdit> finalSet = rolemodelEdits.iterator();
        for (Iterator<UserModel.UserModelEdit> it = finalSet; it.hasNext(); ) {
            UserModel.UserModelEdit fullModel = it.next();
            if (fullModel != null) {
                for (UserModel.UserModelEdit roleModel : userModelEdits) {
                    if (fullModel.getId() == roleModel.getId()) {
                        finalSet.remove();
                    }
                }
            }
        }

        Iterator<UserModel.UserModelBranchEdit> finalSetBranch = branchmodelEdits.iterator();
        for (Iterator<UserModel.UserModelBranchEdit> it = finalSetBranch; it.hasNext(); ) {
            UserModel.UserModelBranchEdit fullModel = it.next();
            if (fullModel != null) {
                for (UserModel.UserModelBranchEdit branchModel : userModelBranchEdits) {
                    if (fullModel.getId() == branchModel.getId()) {
                        finalSetBranch.remove();
                    }
                }
            }
        }

        return new GenericResponse(true,"get User details")
                .setProperty("userId",userDetails.getId())
                .setProperty("email",userDetails.getEmail())
                .setProperty("roles",userModelEdits)
                .setProperty("fullroles",rolemodelEdits)
                .setProperty("fullbranch",branchmodelEdits)
                .setProperty("branch",userModelBranchEdits);
    }

    /**
     * Method to Activate or Deactivate User
     *
     * @param model
     * @param request
     * @param userId
     * @return activated or deactivated userId
     */
    @GetMapping("/admin/getActivateOrDeactivateUser")
    @ResponseBody
    public GenericResponse getUserActivate(Model model, HttpServletRequest request, @RequestParam("userId") Long userId) {
        MastroLogUtils.info(UserController.class, "Going to Activate or Deactivate User :{}" + userId);
        User userDetails = userService.getUserById(userId);
        userService.activateOrDeactivateUser(userId);
        return new GenericResponse(true,"get User details")
                .setProperty("userId",userDetails.getId());
    }

    /**
     * Method to delete User
     *
     * @param model
     * @param request
     * @param userId
     * @return deleted User list
     */
    @PostMapping("/admin/deleteUserDetails")
    @ResponseBody
    public GenericResponse deleteRoles(Model model, HttpServletRequest request, @RequestParam("userId") Long userId) {

        MastroLogUtils.info(UserController.class, "Going to delete User : {}");
        try {
            if (userId != null) {
                userService.deleteUserDetails(userId);
                return new GenericResponse(true, "delete User Details");
            } else {
                return new GenericResponse(false, "User id null");
            }
        } catch (Exception e) {
            MastroLogUtils.error(UserController.class, "Error occured while deleting User : {}", e);
            return new GenericResponse(false, e.getMessage());
        }
    }

    /**
     * Method to get current User
     *
     * @return current logged in User details
     */
    public User getCurrentUser() {
        User userDetails = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.isAuthenticated()) {
            CurrentUserDetails currentUser = (CurrentUserDetails) auth.getPrincipal();
            userDetails = userService.findByEmail(currentUser.getUser().getEmail());
        }
        return userDetails;
    }

}
