package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class MomentPublishForm {
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotNull(message = "权限不能为空")
    private Boolean isPrivate;
    @NotNull(message = "类型不能为空")
    private Boolean isShare;
    //原动态uniqueId
    private String originId;
}
