package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class ResetPasswordModel {

    private String userName;

    @NotEmpty()
    @Size(min = 5, max = 50)
    private String password;

    @NotEmpty()
    @Size(min = 5, max = 50)
    private String confirmPassword;

    private Long userId;

}
