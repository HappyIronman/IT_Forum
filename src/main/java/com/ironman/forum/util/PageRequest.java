package com.ironman.forum.util;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class PageRequest {
    @Min(value = 0, message = "页数不合法")
    private int page = 0;
    @Min(value = 1, message = "页大小不合法")
    @Max(value = 50, message = "页大小不合法")
    private int size = 5;
    private String orderBy = "create_time";
    private String sort = Sort.DESC.name;

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

    public PageRequest(int page, int size, String orderBy, Sort sort) {
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;
        this.sort = sort.name;
    }

    public PageRequest(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort.name;
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

    public String getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort.name;
    }

    public void nextPage() {
        this.page++;
    }

    public enum Sort {
        ASC("ASC"),
        DESC("DESC");
        private String name;

        Sort(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
