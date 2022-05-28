package com.home.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.home.constant.MessageConstant;
import com.home.entity.PageResult;
import com.home.entity.QueryPageBean;
import com.home.entity.Result;
import com.home.model.CheckGroup;
import com.home.model.CheckItem;
import com.home.service.CheckGroupService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 检查组
 * @date 2022/4/30 9:46
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference(interfaceClass = com.home.service.CheckGroupService.class, version = "1.0.0",check = false)
    private CheckGroupService checkGroupService;

    @PostMapping("/pagingQueryCheckGroup.do")
    public PageResult pagingQueryCheckGroup(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.pagingQuery(queryPageBean);
        return pageResult;
    }

    @GetMapping("/queryCheckItemAll.do")
    public Result queryCheckItemAll(){
        List<CheckItem> checkItems = checkGroupService.queryCheckItemAll();
        return new Result(true,"",checkItems);
    }

    @PostMapping("/addCheckGroup.do")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup,Integer[] ids){
        Result result = null;
        try{
            checkGroupService.addCheckGroup(checkGroup,ids);
            result = new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return result;
    }
    @GetMapping("/querySelectedCheckItem.do")
    public Object querySelectedCheckItem(Integer id){
        List<Integer> list = checkGroupService.queryCheckGroupAssociationCheckItem(id);
        return new Result(true,"",list);
    }

    @PostMapping("/editCheckGroupAssociationCheckItem.do")
    public Object editCheckGroupAssociationCheckItem(@RequestBody CheckGroup checkGroup, Integer[] ids){
        System.out.println(checkGroup);
        Result result = null;
        try{
            checkGroupService.editCheckGroupAssociationCheckItem(checkGroup,ids);
            result = new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return result;
    }
    @PostMapping("/deleteCheckGroup.do")
    public Result deleteCheckGroup(Integer id){
        System.out.println(id);
        System.out.println(id + "====");

        Result result = null;
        try {
            checkGroupService.removeCheckGroup(id);
            result = new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result = new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return result;
    }
}
