/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userinfosecond.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.msg.entity.MsgPush;
import com.jeesite.modules.userinfosecond.entity.Userinfosecond;
import com.jeesite.modules.userinfosecond.messageTest.CustomSmsSendService;
import com.jeesite.modules.userinfosecond.messageTest.PushMessage;
import com.jeesite.modules.userinfosecond.service.UserinfosecondService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;

/**
 * 第二版用户名，测试多租户Controller
 * @author blooze
 * @version 2019-03-08
 */
@Controller("userinfosecondController")
@RequestMapping(value = "${adminPath}/userinfosecond/userinfosecond")
public class UserinfosecondController extends BaseController {
	@Autowired
	private PushMessage pushMessage;

	@Autowired
	private CustomSmsSendService customSmsSendService;

	@Autowired
	private UserinfosecondService userinfosecondService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Userinfosecond get(String id, boolean isNewRecord) {
		return userinfosecondService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("userinfosecond:userinfosecond:view")
	@RequestMapping(value = {"list", ""})
	public String list(Userinfosecond userinfosecond, Model model) {
		model.addAttribute("userinfosecond", userinfosecond);
		return "modules/userinfosecond/userinfosecondList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("userinfosecond:userinfosecond:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Userinfosecond> listData(Userinfosecond userinfosecond, HttpServletRequest request, HttpServletResponse response) {
		userinfosecond.setPage(new Page<>(request, response));
		Page<Userinfosecond> page=null;
		if(redisTemplate.hasKey("userinfosecondPage")){
			page=(Page<Userinfosecond>) redisTemplate.opsForValue().get("userinfosecondPage");
			System.out.println("进入userinfosecondPage的redis缓存");
		}else{
			page = userinfosecondService.findPage(userinfosecond);
			redisTemplate.opsForValue().set("userinfosecondPage",page);
		}

		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("userinfosecond:userinfosecond:view")
	@RequestMapping(value = "form")
	public String form(Userinfosecond userinfosecond, Model model) {
		model.addAttribute("userinfosecond", userinfosecond);
		return "modules/userinfosecond/userinfosecondForm";
	}

	/**
	 * 保存用户管理第二版
	 */
	@RequiresPermissions("userinfosecond:userinfosecond:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Userinfosecond userinfosecond) {
		if(redisTemplate.hasKey("userinfosecondPage")){
			redisTemplate.delete("userinfosecondPage");
		}
		userinfosecondService.save(userinfosecond);
		return renderResult(Global.TRUE, text("保存用户管理第二版成功！"));
	}
	
	/**
	 * 停用用户管理第二版
	 */
	@RequiresPermissions("userinfosecond:userinfosecond:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Userinfosecond userinfosecond) {
		userinfosecond.setStatus(Userinfosecond.STATUS_DISABLE);
		userinfosecondService.updateStatus(userinfosecond);
		return renderResult(Global.TRUE, text("停用用户管理第二版成功"));
	}
	
	/**
	 * 启用用户管理第二版
	 */
	@RequiresPermissions("userinfosecond:userinfosecond:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Userinfosecond userinfosecond) {
		userinfosecond.setStatus(Userinfosecond.STATUS_NORMAL);
		userinfosecondService.updateStatus(userinfosecond);
		return renderResult(Global.TRUE, text("启用用户管理第二版成功"));
	}

	/**
	 * 测试jeesite推送消息！！！！！！！！！！！！！！
	 *
	 * @return
	 */
	@RequiresPermissions("userinfosecond:userinfosecond:sendMessage")
	@RequestMapping(value="sendMessage")
	@ResponseBody
	public String sendMessage(Userinfosecond userinfosecond){
		//测试普通发送pc端消息提示。
		pushMessage.push("pc","你好","这是一条测试数据","1100593854261055488","1","user20_80vv");
		//测试发送email给用户
//		MsgPush msgPush=new MsgPush();
//		msgPush.setMsgType("email");
//		msgPush.setMsgTitle("测试发送邮件");
//		msgPush.setMsgContent("这是一条邮件的测试信息。");
//		msgPush.setReceiveUserCode("1459264166@qq.com");
//		customSmsSendService.sendMessage(msgPush);
		return renderResult(Global.TRUE,text("发送消息成功！"));
	}

	/**
	 * 测试定时任务调度过程。
	 */
	public void testJob(){
		System.out.println("任务调度测试成功！！！");
		logger.info("任务调度测试成功！！！");
	}
	
	/**
	 * 删除用户管理第二版
	 */
	@RequiresPermissions("userinfosecond:userinfosecond:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Userinfosecond userinfosecond) {
		userinfosecondService.delete(userinfosecond);
		return renderResult(Global.TRUE, text("删除用户管理第二版成功！"));
	}
	
}