package com.home.service;

import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.model.CheckGroup;
import com.home.model.Setmeal;

import java.util.List;

/**
 * @date 2022/5/6 15:37
 */
public interface SetMealService {
    // 分页查询
    PageResult querySetMealByPagination(QueryPageBean queryPageBean);
    // 查询所有的检查组
    List<CheckGroup> queryCheckGroupAll();
    // 添加检查套餐
    void addSetMeal(Setmeal setmeal, Integer[] checkGroupId);
}
