package com.erp.mastro.controller;


import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.common.UrlUtils;
import com.erp.mastro.entities.User;
import com.erp.mastro.exception.InvalidTokenException;
import com.erp.mastro.exception.MastroEntityException;
import com.erp.mastro.exception.TokenExpiredException;
import com.erp.mastro.model.request.ResetPasswordModel;
import com.erp.mastro.model.request.UserModel;
import com.erp.mastro.repository.UserRepository;
import com.erp.mastro.service.UserServiceImpl;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class ResetPasswordController {

    /**
     * Password reset controller
     */

    @Autowired
    private MessageSource messages;

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserRepository userRepository;


    /**
     * Method to validate token
     *
     * @param request
     * @param id
     * @param token
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(HttpServletRequest request,
                                         @RequestParam("id") long id, @RequestParam("token") String token) {
        MastroLogUtils.info(ResetPasswordController.class, "Going to validate token ");
        try {
            userService.validatePasswordResetToken(id, token, request);
            return "redirect:/updatePassword?id=" + id;
        } catch (TokenExpiredException | InvalidTokenException e) {
            return "redirect:/login?tokenExpired";
        } catch (Exception e) {
            MastroLogUtils.error(this, "Unknown Error occured while task - Change Password. UserId : {}", id, e);
            return "redirect:/login?serverError";
        }
    }

    /**
     * Method to load the create password page
     *
     * @param model
     * @return
     */

    @GetMapping("/updatePassword")
    public String changepassword(Model model, @RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        String userName = user.getUserName();
        model.addAttribute("changepasswordform", new ResetPasswordModel());
        model.addAttribute("userName", userName);
        model.addAttribute("userid", id);
        return "views/create_new_password";
    }

    /**
     * Method to load the forgot password page
     *
     * @param model
     * @return
     */
    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        model.addAttribute("forgotpasswordform", new UserModel());
        return "views/forgot_password";
    }

    /**
     * Method to confirm password
     *
     * @param locale
     * @param errors
     * @return
     */
    @RequestMapping(value = "/confirmPassword", method = RequestMethod.POST)
    public String savePassword(Locale locale, @Valid @ModelAttribute("changepasswordform") ResetPasswordModel resetModel, Errors errors) {
        MastroLogUtils.info(ResetPasswordController.class, "Going to create password after registration: {}");
        if (errors.hasErrors()) {
            return "views/create_new_password";
        } else {
            try {
                if (resetModel.getUserId() != null) {
                    User user = userServiceImpl.getUserById(resetModel.getUserId());
                    userServiceImpl.saveChangedPassword(user, resetModel.getPassword());
                }
                return "redirect:/login";
            } catch (Exception e) {
                MastroLogUtils.error(ResetPasswordController.class, "Errror occurred while creating password :{}", e);
                return "redirect:/updatePassword";
            }
        }
    }

    /**
     * @param userModel
     * @param request
     * @return
     */
    @RequestMapping(value = "/forgotPasswordAction", method = RequestMethod.POST)
    public String forgotPasswordAction(@Valid @ModelAttribute("forgotpasswordform") UserModel userModel, HttpServletRequest request) {
        MastroLogUtils.info(ResetPasswordController.class, "Going to reset password : {}");
        String email = userModel.getEmail();
        try {
            if (userService.isEmailCorrect(email)) {
                userService.sendResetPasswordEmail(email, UrlUtils.getAppurl(request), request.getLocale());
            } else {
                return "views/forgot_password";
            }
        } catch (MastroEntityException e) {
            MastroLogUtils.error(ResetPasswordController.class, "Error occured while resseting password : {}", e);
            return "views/forgot_password";
        }

        return "redirect:/login";

    }

}
