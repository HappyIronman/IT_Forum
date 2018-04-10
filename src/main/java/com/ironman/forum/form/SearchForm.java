package com.ironman.forum.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class SearchForm {
    @Min(value = 0, message = "�������Ͳ��Ϸ�")
    @Max(value = 4, message = "�������Ͳ��Ϸ�")
    private int type;

    @Length(max = 20, message = "��������̫���賤�˰�")
    private String keywords;

    @Min(value = 0, message = "ҳ�����Ϸ�")
    private int page = 0;

    @Min(value = 1, message = "ҳ��С���Ϸ�")
    @Max(value = 50, message = "ҳ��С���Ϸ�")
    private int size = 10;
}
