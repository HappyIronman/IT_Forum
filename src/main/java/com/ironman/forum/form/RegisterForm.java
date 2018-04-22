package com.ironman.forum.form;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RegisterForm {
    @NotBlank(message = "用户名不能为空")
    @Length(max = 12, message = "用户名最长12个字符")
    private String username;
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "手机号不合法")
    private String phone;
    @NotBlank(message = "密码不能为空")
    private String password;


}
