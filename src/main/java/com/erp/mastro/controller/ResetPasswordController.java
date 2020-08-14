package com.erp.mastro.controller;


import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.User;
import com.erp.mastro.exception.InvalidTokenException;
import com.erp.mastro.exception.TokenExpiredException;
import com.erp.mastro.model.request.ResetPasswordModel;
import com.erp.mastro.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    UserService userService;


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
            return "redirect:/updatePassword";
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
    public String changepassword(Model model) {
        model.addAttribute("changepasswordform", new ResetPasswordModel());
        return "views/create_new_password";
    }

    /**
     * Method to confirm password
     *
     * @param locale
     * @param resetModel
     * @param errors
     * @return
     */
    @RequestMapping(value = "/confirmPassword", method = RequestMethod.POST)
    public String savePassword(Locale locale, @Valid @ModelAttribute("changepasswordform") ResetPasswordModel resetModel, Errors errors) {
        MastroLogUtils.info(ResetPasswordController.class, "Going to confirm password : {}");
        if (errors.hasErrors()) {
            return "views/create_new_password";
        } else {
            try {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                userService.saveChangedPassword(user, resetModel.getPassword());
                return "redirect:/login";
            } catch (Exception e) {
                return "redirect:/updatePassword";
            }
        }
    }


}
