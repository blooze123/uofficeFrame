/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.userinfo.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.userinfo.entity.Userinfo;

/**
 * 用户信息管理demoDAO接口
 * @author blooze
 * @version 2019-03-05
 */
@MyBatisDao
public interface UserinfoDao extends CrudDao<Userinfo> {

    Userinfo getSingleUser(String id);
}