package com.home.service;

import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.model.CheckGroup;
import com.home.model.CheckItem;

import java.util.List;

/**
 * @date 2022/4/30 10:16
 */
public interface CheckGroupService {
    PageResult pagingQuery(QueryPageBean queryPageBean);
    List<CheckItem> queryCheckItemAll();
    void addCheckGroup(CheckGroup checkGroup, Integer[] ids);
    List<Integer> queryCheckGroupAssociationCheckItem(Integer id);
    void editCheckGroupAssociationCheckItem(CheckGroup checkGroup, Integer[] ids);
    void removeCheckGroup(Integer id);
}
