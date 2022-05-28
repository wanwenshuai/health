package com.home.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.home.constant.RedisConstant;
import com.home.dao.CheckGroupDao;
import com.home.dao.SetMealDao;
import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.model.CheckGroup;
import com.home.model.Setmeal;
import com.home.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @date 2022/5/6 15:39
 */
@Component
@Service(interfaceClass = com.home.service.SetMealService.class, version = "1.0.0", timeout = 5000)
@Transactional
public class SetMealServiceImpl implements SetMealService{

    @Autowired
    private SetMealDao setMealDao;
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public PageResult querySetMealByPagination(QueryPageBean queryPageBean) {
        // 取出数据
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> setmeals = setMealDao.selectSetMealByPagination(queryString);
        long total = setmeals.getTotal();
        List<Setmeal> result = setmeals.getResult();
        return new PageResult(total,result);
    }

    @Override
    public List<CheckGroup> queryCheckGroupAll() {
        Page<CheckGroup> checkGroups = checkGroupDao.selectCheckGroupByPagination(null);
        return checkGroups;
    }

    @Override
    public void addSetMeal(Setmeal setmeal, Integer[] checkGroupId) {
        int insertSetMealCount = setMealDao.insertSetMeal(setmeal);
        if (insertSetMealCount != 1) throw new RuntimeException();

        // 只有在勾选检查组的情况下才向关系表中添加
        if (!(checkGroupId.length == 1 && checkGroupId[0] == 0)){
            int count = setMealDao.insertSetMealAssociationCheckGroup(setmeal.getId(), checkGroupId);
            if (count == 0) throw new RuntimeException();
        }
        // 将图片名称保存到redis集合中
        String img = setmeal.getImg();
        redisUtils.sSet(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
    }
}
