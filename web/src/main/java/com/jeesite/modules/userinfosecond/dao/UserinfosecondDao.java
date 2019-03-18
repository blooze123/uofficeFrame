/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userinfosecond.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.userinfosecond.entity.Userinfosecond;

/**
 * 第二版用户名，测试多租户DAO接口
 * @author blooze
 * @version 2019-03-08
 */
@MyBatisDao
public interface UserinfosecondDao extends CrudDao<Userinfosecond> {
	
}