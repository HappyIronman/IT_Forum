package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentPublishForm {
    @NotBlank(message = "评论内容不能为空")
    private String content;
    @NotBlank(message = "回复主体不能为空")
    private String replyId;
    @NotNull(message = "类型不能为空")
    @Min(value = 0, message = "类型不合法")
    @Max(value = 3, message = "类型不合法")
    private int type;
}
