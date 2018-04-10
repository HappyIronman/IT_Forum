package com.ironman.forum.form;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RegisterForm {
    @NotBlank(message = "�û�������Ϊ��")
    @Length(max = 12, message = "�û�������ܳ���12���ַ�")
    private String username;
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "�ֻ��Ų��Ϸ�")
    private String phone;
    @NotBlank(message = "���벻��Ϊ��")
    private String password;


}
