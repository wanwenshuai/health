package com.home.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.home.dao.CheckGroupDao;
import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.model.CheckGroup;
import com.home.model.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @date 2022/4/30 10:17
 */
@Component
@Service(interfaceClass = com.home.service.CheckGroupService.class, version = "1.0.0", timeout = 15000)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public PageResult pagingQuery(QueryPageBean queryPageBean) {
        // 拿出封装查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        // SQL拦截，拼接分页条件
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> checkGroups = checkGroupDao.selectCheckGroupByPagination(queryString);
        // 根据查询结果，取出信息
        long total = checkGroups.getTotal();
        List<CheckGroup> checkGroupList = checkGroups.getResult();
        return new PageResult(total,checkGroupList);
    }

    @Override
    public List<CheckItem> queryCheckItemAll() {
        List<CheckItem> checkItems = checkGroupDao.selectCheckItemAll();
        return checkItems;
    }

    @Override
    public void addCheckGroup(CheckGroup checkGroup, Integer[] ids) {
        int insertCount = checkGroupDao.insertCheckGroup(checkGroup);
        if (insertCount != 1) throw new RuntimeException();

        if (!(ids.length == 1 && ids[0]==0)){ // 勾选检查项才插入
            Integer checkGroupId = checkGroup.getId();
            int groupAndCheckItems = checkGroupDao.insertChekGroupAndCheckItem(checkGroupId, ids);
            if (groupAndCheckItems == 0) throw new RuntimeException();
        }
    }

    @Override
    public List<Integer> queryCheckGroupAssociationCheckItem(Integer id) {
        List<Integer> integerList = checkGroupDao.selectCheckGroupAssociationCheckItem(id);
        return integerList;
    }

    @Override
    public void editCheckGroupAssociationCheckItem(CheckGroup checkGroup, Integer[] ids) {
        int updateCheckGroup = checkGroupDao.updateCheckGroup(checkGroup);
        // 没有更新成功，抛出异常
        if (updateCheckGroup != 1) throw new RuntimeException();

        if (!(ids.length == 1 && ids[0]==0)){ // 勾选检查项才更新
            Integer checkGroupId = checkGroup.getId();
            // 先删除检查组关联的检查项
            int deleteCheckGroupAssociationCheckItem = checkGroupDao.deleteCheckGroupAssociationCheckItem(checkGroupId);
            if (deleteCheckGroupAssociationCheckItem == 0) throw new RuntimeException();
            // 在插入选中的检查项
            int groupAndCheckItems = checkGroupDao.insertChekGroupAndCheckItem(checkGroupId, ids);
            if (groupAndCheckItems == 0) throw new RuntimeException();
        }
    }

    @Override
    public void removeCheckGroup(Integer id) {
        if (null != id) {
            // 检查组有检查项，才可以解除关系
            int deleteCount = checkGroupDao.deleteCheckGroupAssociationCheckItem(id);
            // 再删除检查组
            int i = checkGroupDao.deleteCheckGroup(id);
            if (i != 1) throw new RuntimeException();
        }
    }
}
