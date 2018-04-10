package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserEditForm {
    private String profile;
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "手机号不合法")
    private String phone;
    @Email(message = "邮箱格式不合法")
    private String email;
    @Min(value = 0, message = "性别不合法")
    @Max(value = 2, message = "性别不合法")
    private Integer sex = 0;
    @Length(max = 30, message = "学校名字guoc")
    private String school;
    @Length(max = 30, message = "自我介绍过长")
    private String intro;
}
