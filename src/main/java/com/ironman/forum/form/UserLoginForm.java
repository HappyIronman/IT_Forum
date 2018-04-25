package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class UserLoginForm {
    @NotBlank(message = "用户名（手机号）不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
