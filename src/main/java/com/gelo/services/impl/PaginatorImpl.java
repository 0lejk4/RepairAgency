package com.gelo.services.impl;

import com.gelo.services.Paginator;
import com.gelo.validation.PaginationForm;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class PaginatorImpl<T> implements Paginator<T> {
    private String orderField;
    private Boolean ascending;
    private Integer count;
    private Integer page;
    private Integer pageCount;
    private List<T> entities = Collections.emptyList();
    private boolean isValid;

    public void paginate(PaginationForm form, Callable<List<T>> callable, long allCount) {
        isValid = form.valid();
        if (isValid) {
            pageCount = ((int) Math.ceil(((double) allCount) / form.getCount()));

            try {
                entities = callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }

            orderField = form.getOrderField();
            ascending = form.getAscending();
            count = form.getCount();
            page = form.getPage();
        } else {
            orderField = "id";
            ascending = true;
            count = 20;
            page = 1;
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public String getOrderField() {
        return orderField;
    }

    public Boolean getAscending() {
        return ascending;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getPage() {
        return page;
    }

    public List<T> getEntities() {
        return entities;
    }
}
