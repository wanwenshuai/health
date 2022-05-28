package com.home.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.home.dao.OrderSettingDao;
import com.home.model.OrderSetting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @date 2022/5/26 14:40
 */
@Component
@Service(interfaceClass = com.home.service.OrderSettingService.class, version = "1.0.0", timeout = 15000)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void addNumberOfPeopleAvailable(List<String[]> row) {

        if (null != row && row.size() > 0){
            for (String[] cell : row) {
                String date = cell[0];
                System.out.println(date);
                String number = cell[1];
                OrderSetting orderSetting = new OrderSetting(new Date(date),Integer.parseInt(number));
                System.out.println(orderSetting.getOrderDate());
                // 判断表中是否有当前日期
                long count = orderSettingDao.selectCountByDate(orderSetting.getOrderDate());
                int i;
                if (count > 0){ // 有数据,进行更新
                    i = orderSettingDao.updateNumberByDate(orderSetting);
                }else {
                    i = orderSettingDao.addExcelRow(orderSetting);
                }
                if (i == 0) throw new RuntimeException();
            }
           /* row.forEach(cell->{

            });*/
        }
    }
}

