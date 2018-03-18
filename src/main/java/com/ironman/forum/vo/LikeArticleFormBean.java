package com.ironman.forum.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LikeArticleFormBean {
    @NotBlank(message = "文章uniqueId不能为空")
    private String targetId;
    @NotNull(message = "文章类型不能为空")
    @Min(value = 0, message = "类型值不合法")
    @Max(value = 3, message = "类型值不合法")
    private Integer type;
    private boolean like = true;
}
