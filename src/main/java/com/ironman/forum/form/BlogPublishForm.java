package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BlogPublishForm {
    @NotBlank(message = "���ⲻ��Ϊ��")
    private String title;
    @NotBlank(message = "���ݲ���Ϊ��")
    private String content;
    @NotNull(message = "Ȩ�޲���Ϊ��")
    private Boolean isPrivate;
    @NotNull(message = "�Ƿ�Ϊ������Ϊ��")
    private Boolean isShare;
    //ԭ����uniqueId
    private String originId;
}
