package com.home.dao;

import com.home.model.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @date 2022/5/26 16:15
 */
@Repository
public interface OrderSettingDao {
    // 添加表格中的数据
    int addExcelRow(OrderSetting orderSetting);
    // 根据日期查询是否已经添加
    long selectCountByDate(@Param("orderDate") Date orderDate);
    // 根据日期编辑插入的记录
    int updateNumberByDate(OrderSetting orderSetting);
}
