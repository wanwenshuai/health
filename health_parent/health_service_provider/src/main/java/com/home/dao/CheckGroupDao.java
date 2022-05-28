package com.home.dao;

import com.github.pagehelper.Page;
import com.home.model.CheckGroup;
import com.home.model.CheckItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @date 2022/4/30 9:35
 */
@Repository
public interface CheckGroupDao {
    // 分页查询检查组信息
    Page<CheckGroup> selectCheckGroupByPagination(@Param("queryString") String queryString);
    // 查询所有的检查项
    List<CheckItem> selectCheckItemAll();
    // 插入检查组
    int insertCheckGroup(CheckGroup checkGroup);
    // 插入检查组和对应的检查项
    int insertChekGroupAndCheckItem(@Param("checkGroupId") Integer checkGroupId,
                                    @Param("checkItemId") Integer[] checkItemId);
    // 查询检查组对应的检查项
    List<Integer> selectCheckGroupAssociationCheckItem(@Param("id") Integer id);

    // 编辑检查组
    int updateCheckGroup(CheckGroup checkGroup);
    // 编辑检查组和对应的检查项
    // 删除对应的检查组关联的检查项,再调用insertChekGroupAndCheckItem()
    int deleteCheckGroupAssociationCheckItem(@Param("checkGroupId") Integer checkGroupId);

    // 删除检查组
    int deleteCheckGroup(@Param("checkGroupId") Integer checkGroupId);

}
