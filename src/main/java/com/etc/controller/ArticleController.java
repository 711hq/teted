package com.etc.controller;

import com.etc.entity.Article;
import com.etc.feigninters.UsersFeignClient;
import com.etc.service.ArticleService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;
    @Resource
    private RestTemplate restTemplate;


    @Resource
    private UsersFeignClient usersFeignClient;


    //使用feign调用用户的微服务
    @RequestMapping("/getdetailbyfeign/{articleId}")
    public Map<String,Object> getArticleDetailByFeign(@PathVariable Integer articleId, HttpSession session){
        Object user = session.getAttribute("user");
        System.out.println("从springcloudzuul中取：" + user);
        Article a = articleService.getById(articleId);
        //调用feign接口中定义的方法
        Map<String,Object> map = usersFeignClient.getUserById(a.getAuthorId());
        map.put("articleId",a.getArticleId());
        map.put("articleTitle",a.getArticleTitle());
        map.put("articleContent",a.getArticleContent());
        map.put("articleDt",a.getArticleDate());
        return map;
    }
    //断路器的设置
    @HystrixCommand(fallbackMethod = "getDefaultUser")

    //从文章微服务访问用户微服务
    @RequestMapping("/getuser/{uid}")
    public Map<String,Object> getUser(@PathVariable Integer uid){
        Map<String,Object> map = restTemplate.getForObject("http://localhost:8762/user/get/"+uid,Map.class);

        return map;
    }

    private Map<String,Object> getDefaultUser(Integer uid){
        Map<String,Object> map = new HashMap<>();
        map.put("uname","-1");
        map.put("pwd","-1");
        return map;
    }


    @RequestMapping("/get/{articleId}")
    public Article getArticle(@PathVariable Integer articleId){
        Article a = articleService.getById(articleId);
        return a;
    }
    @RequestMapping("/getdetail/{articleId}")
    public Map<String,Object> getArticleDetail(@PathVariable Integer articleId){
        Article a = articleService.getById(articleId);
        Map<String,Object> map = usersFeignClient.getUserById(a.getAuthorId());


        if (a != null){
            map.put("articleId",a.getArticleId());
            map.put("articleTitle",a.getArticleTitle());
            map.put("articleContent",a.getArticleContent());
            map.put("articleDate",a.getArticleDate());
            map.put("authorId",a.getAuthorId());
        }

        return map;
    }
}
