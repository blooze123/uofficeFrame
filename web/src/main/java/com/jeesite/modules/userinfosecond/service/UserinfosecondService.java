/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userinfosecond.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.userinfosecond.dao.UserinfosecondDao;
import com.jeesite.modules.userinfosecond.entity.Userinfosecond;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 第二版用户名，测试多租户Service
 * @author blooze
 * @version 2019-03-08
 */
@Service
@Transactional(readOnly=true)
public class UserinfosecondService extends CrudService<UserinfosecondDao, Userinfosecond> {
	
	/**
	 * 获取单条数据
	 * @param userinfosecond
	 * @return
	 */
	@Override
	public Userinfosecond get(Userinfosecond userinfosecond) {
		return super.get(userinfosecond);
	}
	
	/**
	 * 查询分页数据
	 * @param userinfosecond 查询条件
	 * @param userinfosecond.page 分页对象
	 * @return
	 */
	@Override
	public Page<Userinfosecond> findPage(Userinfosecond userinfosecond) {
		return super.findPage(userinfosecond);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userinfosecond
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Userinfosecond userinfosecond) {
		super.save(userinfosecond);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(userinfosecond.getId(), "userinfosecond_image");
		// 保存上传附件
		FileUploadUtils.saveFileUpload(userinfosecond.getId(), "userinfosecond_file");
	}
	
	/**
	 * 更新状态
	 * @param userinfosecond
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Userinfosecond userinfosecond) {
		super.updateStatus(userinfosecond);
	}
	
	/**
	 * 删除数据
	 * @param userinfosecond
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Userinfosecond userinfosecond) {
		super.delete(userinfosecond);
	}
	
}