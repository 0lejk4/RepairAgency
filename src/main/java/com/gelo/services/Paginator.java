package com.gelo.services;

import com.gelo.validation.PaginationForm;

import java.util.List;
import java.util.concurrent.Callable;

public interface Paginator<T> {
    void paginate(PaginationForm form, Callable<List<T>> callable, long allCount);

    boolean isValid();

    Integer getPageCount();

    String getOrderField();

    Boolean getAscending();

    Integer getCount();

    Integer getPage();

    List<T> getEntities();
}
