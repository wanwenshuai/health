package com.home.controller;

import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import com.alibaba.dubbo.config.annotation.Reference;
import com.home.constant.MessageConstant;
import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.entity.Result;
import com.home.model.CheckItem;
import com.home.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2022/4/18 11:22
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {
    @Reference(interfaceClass = com.home.service.CheckItemService.class, version = "1.0.0", check = false)
    private CheckItemService checkItemService;

    @PostMapping("/addCheckItem.do")
    public Object addCheckItem(@RequestBody CheckItem checkItem){
        Result result = null;
        try{
            int count = checkItemService.addCheckItem(checkItem);
            if (count > 0){
                result = new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
            }
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return result;
    }
    @PostMapping("/pagingQuery.do")
    public PageResult pagingQuery(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pagingQuery(queryPageBean);
        return pageResult;
    }

    @GetMapping("/removeCheckItem.do")
    public Object removeCheckItem(Integer id){

        Result result = null;
        try {
            checkItemService.removeCheckItemById(id);
            result = new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return result;
    }
    @PostMapping("/editCheckItem.do")
    public Object editCheckItem(@RequestBody CheckItem checkItem){
        Result result = null;
        try{
            checkItemService.editCheckItemById(checkItem);
            result = new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return result;
    }
}
