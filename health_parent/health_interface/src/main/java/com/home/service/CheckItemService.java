package com.home.service;

import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.model.CheckItem;

/**
 * @date 2022/4/18 11:30
 */
public interface CheckItemService {
    int addCheckItem(CheckItem checkItem);

    PageResult pagingQuery(QueryPageBean codeOrItem);

    void removeCheckItemById(Integer id);

    void editCheckItemById(CheckItem checkItem);
}
