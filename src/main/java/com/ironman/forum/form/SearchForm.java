package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class SearchForm {
    @Min(value = 0, message = "搜索类型不合法")
    @Max(value = 4, message = "搜索类型不合法")
    private int type;

    @Length(max = 20, message = "检索内容太他妈长了吧")
    private String keywords;

    @Min(value = 0, message = "页数不合法")
    private int page = 0;

    @Min(value = 1, message = "页大小不合法")
    @Max(value = 50, message = "页大小不合法")
    private int size = 10;
}
