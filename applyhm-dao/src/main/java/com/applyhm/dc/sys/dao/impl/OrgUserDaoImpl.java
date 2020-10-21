package com.applyhm.dc.sys.dao.impl;

import com.applyhm.dc.sys.search.OrgUserSearch;
import org.springframework.stereotype.Repository;

import com.applyhm.core.frame.dao.impl.BaseDaoImpl;
import com.applyhm.dc.sys.dao.OrgUserDao;
import com.applyhm.dc.sys.po.OrgUser;
import com.applyhm.dc.sys.vo.OrgUserVo;

@Repository
public class OrgUserDaoImpl extends BaseDaoImpl<OrgUser, OrgUserSearch> implements OrgUserDao {

	@Override
	public OrgUserVo selectByUserId(Integer userId) {
		return this.sqlSessionTemplate.selectOne(this.getSqlName("selectByUserId"), userId);
	}

	
}
