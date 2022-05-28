package com.home.dao;

import com.github.pagehelper.Page;
import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.model.CheckItem;
import org.springframework.stereotype.Repository;

/**
 * @date 2022/4/18 11:35
 */
@Repository
public interface CheckItemDao {
    int insertCheckItem(CheckItem checkItem);

    Page<CheckItem> pagingSelect(String queryString);

    long selectCheckGroupAndCheckItemCount(Integer checkItemId);

    int deleteCheckItemById(Integer id);

    int updateCheckItem(CheckItem checkItem);

}
