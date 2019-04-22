package com.sunkang.bolg.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;

import com.sunkang.bolg.entity.*;
import com.sunkang.bolg.util.*;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;




@RequestMapping("/login")
@RestController
@CrossOrigin
@ConfigurationProperties("admin.config")
public class AdminController{

   
    private String username;

    private String password;

    private String id;

    @Autowired
    private JwtUtil jwtUtil;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        
        this.username = username;
    }

    public String getPassword() {
      
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getId() {
     
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> admin){
        if(username.equals(admin.get("username"))&& password.equals(admin.get("password"))){
            String token=jwtUtil.createJWT(id, username, "admin");
            Map<String,Object> map=new HashMap<>();
            map.put("token",token);
            map.put("role","admin");
            return new Result(true,StatusCode.OK,"登录成功",map);
        }
            return new Result(false,StatusCode.OK,"用户名或密码错误");
    }

    @RequestMapping(value="info",method = RequestMethod.GET)
    public Result getInfo(String  token){        
        Claims claims = jwtUtil.parseJWT(token);
        String roles=(String)claims.get("roles");
        String name=(String)claims.getSubject();
        String avatar="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
        Map<String,Object> map=new HashMap<>();
        map.put("roles",roles);
        map.put("name",name);
        map.put("avatar",avatar);
        return new Result(true,StatusCode.OK,"登录成功",map);
    }
}
