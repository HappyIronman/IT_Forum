package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class UserLoginForm {
    @NotBlank(message = "�û�������Ϊ��")
    private String username;
    @NotBlank(message = "���벻��Ϊ��")
    private String password;
}
