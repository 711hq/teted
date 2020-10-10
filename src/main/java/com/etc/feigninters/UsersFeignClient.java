package com.etc.feigninters;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
//接口类上加入的注解中添加属性fallback,指定回调类
@FeignClient(name = "springclouduser",fallback = UserFeignClientFallBack.class)  // 服务消费类要调用的服务类名
public interface UsersFeignClient {
    // values请求地址就是users用户服务中该控制器的接口地址
    @RequestMapping(value = "/user/get/{uid}",method = RequestMethod.GET)
    public Map<String,Object> getUserById(@PathVariable("uid") Integer uid);
    @RequestMapping(value = "/user/get/{uid}",method = RequestMethod.GET)
    public Map<String,Object> getArticleDetail(@PathVariable Integer articleId);
}


