package com.etc.feigninters;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserFeignClientFallBack implements UsersFeignClient{


    @Override
    public Map<String, Object> getUserById(Integer uid) {
        Map<String,Object> map = new HashMap<>();
        map.put("uname","-1");
        map.put("pwd","-1");
        return map;
    }

    @Override
    public Map<String, Object> getArticleDetail(Integer articleId) {
        Map<String,Object> map = new HashMap<>();
        map.put("uname","-1");
        map.put("pwd","-1");
        return map;
    }
}
