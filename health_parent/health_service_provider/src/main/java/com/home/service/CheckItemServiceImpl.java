package com.home.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.home.dao.CheckItemDao;
import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.model.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @date 2022/4/18 11:32
 */
@Component
@Service(interfaceClass = com.home.service.CheckItemService.class,version = "1.0.0", timeout = 5000)
@Transactional
public class CheckItemServiceImpl implements CheckItemService{
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public int addCheckItem(CheckItem checkItem) {
        return checkItemDao.insertCheckItem(checkItem);
    }

    @Override
    public PageResult pagingQuery(QueryPageBean queryPageBean) {
        // 查询条件
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        // 分页查询插件
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> checkItems = checkItemDao.pagingSelect(queryString);
        long total = checkItems.getTotal();
        List<CheckItem> result = checkItems.getResult();

        return new PageResult(total,result);
    }

    @Override
    public void removeCheckItemById(Integer id) {
        long count = checkItemDao.selectCheckGroupAndCheckItemCount(id);

        if (count > 0){ // 被关联到检查组，不可删除
            throw new RuntimeException();
        }
        checkItemDao.deleteCheckItemById(id);
    }

    @Override
    public void editCheckItemById(CheckItem checkItem) {
        int count = checkItemDao.updateCheckItem(checkItem);
        if (count != 1){ // 更新失败，抛出异常
            throw new RuntimeException();
        }
    }
}
