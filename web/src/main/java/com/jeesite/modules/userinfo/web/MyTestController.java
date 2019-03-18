package com.jeesite.modules.userinfo.web;

import com.jeesite.modules.userinfo.entity.Userinfo;
import com.jeesite.modules.userinfo.service.UserinfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/userinfo/userinfo")
public class MyTestController {
    @Resource
    private UserinfoService userinfoService;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 尝试自定义controller跳转。
     * @return
     */
    @RequiresPermissions("userinfo:userinfo:view")
    @RequestMapping("selectHobby")
    public String selectHobby(String id, Model model){
        Userinfo user=userinfoService.get(id);
        List<Userinfo> userList=new ArrayList<>();
        userList.add(user);
        Map<String,Object> map=new HashMap<>();
        map.put("user",user);
        //测试前端获取普通POJO类
        model.addAttribute("user",user);
        //测试前端获取list链表
        model.addAttribute("userList",userList);
        //测试前端获取Map
        model.addAttribute("userMap",map);
        return "modules/userinfo/NewPage";
    }
}
