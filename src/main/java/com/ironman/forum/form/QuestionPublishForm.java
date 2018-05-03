package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class QuestionPublishForm {
    @NotBlank(message = "标题不能为空")
    @Length(max = 100, message = "长度不能超过100字符")
    private String title;
    @Length(max = 200, message = "长度不能超过1000字符")
    private String content;
}
