package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LikeArticleFormBean {
    @NotBlank(message = "����uniqueId����Ϊ��")
    private String targetId;
    @NotNull(message = "�������Ͳ���Ϊ��")
    @Min(value = 0, message = "����ֵ���Ϸ�")
    @Max(value = 3, message = "����ֵ���Ϸ�")
    private Integer type;
    private boolean like = true;
}
