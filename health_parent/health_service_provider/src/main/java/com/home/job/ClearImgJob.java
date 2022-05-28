package com.home.job;

import com.home.constant.RedisConstant;
import com.home.utils.QiNiuUtils;
import com.home.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @date 2022/5/18 21:49
 */
@Service
public class ClearImgJob {
    @Autowired
    private RedisUtils redisUtils;

    @Scheduled(cron = "0/50 * * * * ?")
    public void clearImg(){
        // 根据redis中保存的两个集合进行差值计算，获取垃圾图片名称集合
        Set<Object> big = redisUtils.sGet(RedisConstant.SETMEAL_PIC_RESOURCES);
        Set<Object> small = redisUtils.sGet(RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        if (null != big){
            // 删除大集合中包含小集合中的元素
            boolean flag = big.removeAll(small);
            // 遍历大集合中的垃圾元素
            big.forEach(imgName->{
                // 删除七牛云服务器上的图片
                QiNiuUtils.deleteFileFromQiNiu((String)imgName);
                // 删除redis中垃圾图片
                redisUtils.setRemove(RedisConstant.SETMEAL_PIC_RESOURCES,imgName);
                System.out.println(imgName + "已删除");
            });
        }

    }
}
