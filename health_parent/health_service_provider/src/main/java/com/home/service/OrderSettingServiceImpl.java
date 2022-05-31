package com.home.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.home.dao.OrderSettingDao;
import com.home.model.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        }
    }

    @Override
    public List<Map<String,Integer>> queryOrderSettingByMonth(String date) {
        String start = date + "-1";
        String end = date + "-31";

        Map<String,String> startDateAndEndDate = new HashMap<>();
        startDateAndEndDate.put("start",start);
        startDateAndEndDate.put("end",end);

        List<OrderSetting> orderSettings = orderSettingDao.selectOrderSettingByMonth(startDateAndEndDate);

        List<Map<String,Integer>> list = new ArrayList<>();

        orderSettings.forEach(orderSetting -> {
            Map<String,Integer> map = new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            list.add(map);
        });

        return list;
    }

    @Override
    public void orderSettingNumber(OrderSetting orderSetting) {
        // 根据日期查询是否可添加
        long count = orderSettingDao.selectCountByDate(orderSetting.getOrderDate());
        if (count != 1){
            // 说明没有设置该日期的可预约人数
            int i = orderSettingDao.addExcelRow(orderSetting);
            if (i != 1) throw new RuntimeException();
        }else {
            // 执行更新操作
            int i = orderSettingDao.updateNumberByDate(orderSetting);
            if (i != 1) throw new RuntimeException();
        }
    }
}

