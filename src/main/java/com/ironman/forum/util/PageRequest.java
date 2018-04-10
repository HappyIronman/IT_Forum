package com.ironman.forum.util;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class PageRequest {
    @Min(value = 0, message = "页数不合法")
    private int page = 0;
    @Min(value = 1, message = "页大小不合法")
    @Max(value = 50, message = "页大小不合法")
    private int size = 10;
    private String orderBy = "create_time";

    public PageRequest() {
    }

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageRequest(int page, int size, String orderBy) {
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
