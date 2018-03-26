package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class MomentPublishForm {
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotNull(message = "权限不能为空")
    private Boolean isPrivate;
    @NotNull(message = "是否为分享不能为空")
    private Boolean isShare;
    @NotNull(message = "是否包含图片不能为空")
    private Boolean isContainPic;
    //原动态uniqueId
    private String originId;
    //图片名列表
    private List<String> picNameList;
}
