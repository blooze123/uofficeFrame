/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userinfo.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.userinfo.dao.UserinfoDao;
import com.jeesite.modules.userinfo.entity.Userinfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户信息管理demoService
 * @author blooze
 * @version 2019-03-05
 */
@Service
@Transactional(readOnly=true)
public class UserinfoService extends CrudService<UserinfoDao, Userinfo> {
	@Resource
	private UserinfoDao userinfoDao;
	
	/**
	 * 获取单条数据
	 * @param userinfo
	 * @return
	 */
	@Override
	public Userinfo get(Userinfo userinfo) {
		return super.get(userinfo);
	}


	/**
	 * 自定义获取单条数据
	 */
	public Userinfo getSingleUser(String id){
		return userinfoDao.getSingleUser(id);
	}
	
	/**
	 * 查询分页数据
	 * @param userinfo 查询条件
	 * @param userinfo.page 分页对象
	 * @return
	 */
	@Override
	public Page<Userinfo> findPage(Userinfo userinfo) {
		return super.findPage(userinfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userinfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Userinfo userinfo) {
		super.save(userinfo);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(userinfo.getId(), "userinfo_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(userinfo.getId(), "userinfo_file");
	}
	
	/**
	 * 更新状态
	 * @param userinfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Userinfo userinfo) {
		super.updateStatus(userinfo);
	}
	
	/**
	 * 删除数据
	 * @param userinfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Userinfo userinfo) {
		super.delete(userinfo);
	}
	
}