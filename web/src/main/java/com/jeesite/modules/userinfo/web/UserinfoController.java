/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userinfo.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.userinfo.entity.Userinfo;
import com.jeesite.modules.userinfo.service.UserinfoService;
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

/**
 *
 * 用户信息管理demoController
 * @author blooze
 * @version 2019-03-05
 */
@Controller
@RequestMapping(value = "${adminPath}/userinfo/userinfo")
public class UserinfoController extends BaseController {

	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Userinfo get(String id, boolean isNewRecord) {
		return userinfoService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("userinfo:userinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(Userinfo userinfo, Model model) {
		model.addAttribute("userinfo", userinfo);
		return "modules/userinfo/userinfoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("userinfo:userinfo:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Userinfo> listData(Userinfo userinfo, HttpServletRequest request, HttpServletResponse response) {
		userinfo.setPage(new Page<>(request, response));
		//使用redis缓存
		Page<Userinfo> page = null;
		if(redisTemplate.hasKey("UserinfoPage")){
			page=(Page<Userinfo>)redisTemplate.opsForValue().get("UserinfoPage");
			System.out.println("page进入redis缓存");
			return page;
		}else{
			System.out.println("未进入redis");
			page=userinfoService.findPage(userinfo);
			redisTemplate.opsForValue().set("UserinfoPage",page);
			return page;
		}
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("userinfo:userinfo:view")
	@RequestMapping(value = "form")
	public String form(Userinfo userinfo, Model model) {
		model.addAttribute("userinfo", userinfo);
		return "modules/userinfo/userinfoForm";
	}

	/**
	 * 测试自定义分配角色
	 * @param userinfo
	 * @param op
	 * @param model
	 * @return
	 */
	@RequiresPermissions("userinfo:sys:role")
	@RequestMapping(value="sendRole")
	public String assignRole(Userinfo userinfo, String op, Model model){
		if (StringUtils.inString(op, new String[]{"auth"})) {
			Role role = new Role();
			role.setUserCode(userinfo.getUserid());
			model.addAttribute("roleList", this.roleService.findListByUserCode(role));
		}

		model.addAttribute("op", op);
		model.addAttribute("userinfo", userinfo);
		return "modules/userinfo/assignRole";
	}




	/**
	 * 保存用户信息管理
	 */
	@RequiresPermissions("userinfo:userinfo:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Userinfo userinfo) {
		if(redisTemplate.hasKey("UserinfoPage")){
			redisTemplate.delete("UserinfoPage");
		}
		userinfoService.save(userinfo);
		return renderResult(Global.TRUE, text("保存用户信息管理成功！"));
	}
	
	/**
	 * 停用用户信息管理
	 */
	@RequiresPermissions("userinfo:userinfo:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(Userinfo userinfo) {
		userinfo.setStatus(Userinfo.STATUS_DISABLE);
		userinfoService.updateStatus(userinfo);
		return renderResult(Global.TRUE, text("停用用户信息管理成功"));
	}

	
	/**
	 * 启用用户信息管理
	 */
	@RequiresPermissions("userinfo:userinfo:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(Userinfo userinfo) {
		userinfo.setStatus(Userinfo.STATUS_NORMAL);
		userinfoService.updateStatus(userinfo);
		return renderResult(Global.TRUE, text("启用用户信息管理成功"));
	}
	
	/**
	 * 删除用户信息管理
	 */
	@RequiresPermissions("userinfo:userinfo:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Userinfo userinfo) {
		userinfoService.delete(userinfo);
		return renderResult(Global.TRUE, text("删除用户信息管理成功！"));
	}
	
}