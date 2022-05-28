package com.home.dao;

import com.github.pagehelper.Page;
import com.home.model.CheckGroup;
import com.home.model.Setmeal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @date 2022/5/6 15:26
 */
@Repository
public interface SetMealDao {
    // 分页查询套餐列表
    Page<Setmeal> selectSetMealByPagination(@Param("queryCondition") String queryCondition);

    // 添加检查套餐
    int insertSetMeal(Setmeal setmeal);

    // 添加检查套餐对应的检查组
    int insertSetMealAssociationCheckGroup(@Param("setMealId") Integer setMealId,
                                           @Param("checkGroupIds") Integer[] checkGroupIds);
}
