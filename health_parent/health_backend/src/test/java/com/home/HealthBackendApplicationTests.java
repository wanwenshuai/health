package com.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.home.model.User;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class HealthBackendApplicationTests {

    @Test
    void contextLoads() throws Exception {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "KpyndJBRXZOgNF2XwnG2NB6OhYNTRjXkgCH-qUby";
        String secretKey = "IxOVIG_L_JSy6cPX8y68HYSwTLHEEhW1uR2ndntY";
        String bucket = "project-health-space1";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "F:\\BaiduNetdiskDownload\\资料-传智健康项目\\day04\\资源\\图片资源\\03a36073-a140-4942-9b9b-712cecb144901.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "test.jpg";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test1() throws JsonProcessingException {
        Cat cat = new Cat("小猫", 20);
        // jackson对象序列化
        String jsonCat = new ObjectMapper().writeValueAsString(cat);
        redisTemplate.opsForValue().set("cat",jsonCat);

        System.out.println(redisTemplate.opsForValue().get("cat"));
    }

}


class Cat{
    private String name;
    private Integer age;

    public Cat() {
    }

    public Cat(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}