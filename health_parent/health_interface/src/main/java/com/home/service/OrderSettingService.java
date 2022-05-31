package com.home.service;

import com.home.model.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @date 2022/5/26 14:33
 */
public interface OrderSettingService {
    void addNumberOfPeopleAvailable(List<String[]> row);
    List<Map<String,Integer>> queryOrderSettingByMonth(String date);
    void orderSettingNumber(OrderSetting orderSetting);
}
