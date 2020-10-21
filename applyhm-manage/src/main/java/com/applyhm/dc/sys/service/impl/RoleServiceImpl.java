package com.applyhm.dc.sys.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applyhm.core.frame.dao.BaseDao;
import com.applyhm.core.frame.service.impl.BaseServiceImpl;
import com.applyhm.dc.sys.dao.RoleDao;
import com.applyhm.dc.sys.po.Role;
import com.applyhm.dc.sys.search.RoleSearch;
import com.applyhm.dc.sys.service.RoleService;


@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,RoleSearch> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	protected BaseDao<Role,RoleSearch> getBaseDao() {
		return roleDao;
	}

	

}
