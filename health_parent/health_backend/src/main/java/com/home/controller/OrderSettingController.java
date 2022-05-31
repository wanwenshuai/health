package com.home.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.home.constant.MessageConstant;
import com.home.entity.Result;
import com.home.model.OrderSetting;
import com.home.service.OrderSettingService;
import com.home.utils.POIUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @date 2022/5/26 14:05
 */
@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference(interfaceClass = com.home.service.OrderSettingService.class, version = "1.0.0", check = false)
    private OrderSettingService orderSettingService;

    @GetMapping(value = "/download.do")
    public void test(HttpServletResponse response) {
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        try {
            String fileName = "ordersetting_template.xlsx";
            //设置响应头
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            String path = "/excel/" + fileName;
            fis = new FileInputStream(new ClassPathResource(path).getFile());
            sos = response.getOutputStream();
            IOUtils.copy(fis, sos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("模板下载失败！");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (sos != null) {
                    sos.flush();
                    sos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 读取文件
    @PostMapping(value = "/upload.do")
    public Object upload(@RequestParam("excelFile") MultipartFile multipartFile){
        // 读取文件中的内容
        try{
            List<String[]> rows = POIUtils.readExcel(multipartFile);
            orderSettingService.addNumberOfPeopleAvailable(rows);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    // 获取预约人数信息
    @PostMapping("/getOrderSettingByMonth.do")
    public Result getOrderSettingByMonth(String date){
        Result result = null;
        try{
            List<Map<String,Integer>> maps = orderSettingService.queryOrderSettingByMonth(date);
            result = new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,maps);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
        return result;
    }

    // 设置预约人数
    @PostMapping("/settingOrderNumber.do")
    public Result settingOrderNumber(@RequestBody OrderSetting orderSetting){
        Result result = null;
        try {
            orderSettingService.orderSettingNumber(orderSetting);
            result = new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
        return result;
    }
}
