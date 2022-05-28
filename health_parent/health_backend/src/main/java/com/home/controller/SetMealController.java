package com.home.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.home.constant.MessageConstant;
import com.home.constant.RedisConstant;
import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.entity.Result;
import com.home.model.Setmeal;
import com.home.service.SetMealService;
import com.home.utils.QiNiuUtils;
import com.home.utils.RedisUtils;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


/**
 * @date 2022/5/6 15:12
 */
@RestController
@RequestMapping("/setMeal")
public class SetMealController {

    @Reference(interfaceClass = com.home.service.SetMealService.class, version = "1.0.0", check = false)
    private SetMealService setMealService;
    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/pagination.do")
    public PageResult pagination(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setMealService.querySetMealByPagination(queryPageBean);
        return pageResult;
    }

    @GetMapping("/queryCheckGroupAll.do")
    public Result queryCheckGroupAll(){
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,setMealService.queryCheckGroupAll());
    }

    @PostMapping("/upload.do")
    public Result upload(@RequestParam("imgFile") MultipartFile multipartFile){
        Result result = null;
        try {
            // 获取文件后缀名
            String fileName = multipartFile.getOriginalFilename();

            int index = fileName.lastIndexOf('.');
            String suffix = fileName.substring(index); // .jpg

            // 把传过来的文件上传的七牛云 文件名采用UUID
            String uuid = UUID.randomUUID().toString();
            QiNiuUtils.upload2QiNiu(multipartFile.getBytes(),uuid+suffix);
            result = new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,uuid+suffix);
            // 将上传图片的名称存入redis，基于redis的set集合存储
            redisUtils.sSet(RedisConstant.SETMEAL_PIC_RESOURCES,uuid+suffix);
        } catch (IOException e) {
            e.printStackTrace();
            result = new Result(true,MessageConstant.PIC_UPLOAD_FAIL);
        }
        return result;
    }

    // 添加套餐
    @PostMapping("/addSetMeal.do")
    public Result addSetMeal(@RequestBody Setmeal setmeal, Integer[] checkGroupId){
        Result result = null;
        try{
            setMealService.addSetMeal(setmeal,checkGroupId);
            result = new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
             result = new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return result;
    }
}
