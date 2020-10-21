package com.applyhm.dc.sys.dao;

import com.applyhm.core.frame.dao.BaseDao;
import com.applyhm.dc.sys.search.OrgUserSearch;
import com.applyhm.dc.sys.po.OrgUser;
import com.applyhm.dc.sys.vo.OrgUserVo;

/**
 * 系统用户操作类接口
 */
public interface OrgUserDao extends BaseDao<OrgUser, OrgUserSearch> {
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public OrgUserVo selectByUserId(Integer userId);
	
	
}
