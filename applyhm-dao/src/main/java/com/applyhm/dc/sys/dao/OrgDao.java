package com.applyhm.dc.sys.dao;

import java.util.List;
import java.util.Map;

import com.applyhm.core.frame.dao.BaseDao;
import com.applyhm.dc.sys.search.OrgSearch;
import com.applyhm.dc.sys.po.Org;
import com.applyhm.dc.sys.vo.OrgVo;

public interface OrgDao extends BaseDao<Org, OrgSearch> {
	
	/**
	 * 批量插入角色菜单
	 * @param roleOrgList
	 * @return
	 */
	public Boolean batchInsertRoleOrg(List<Map<String, Object>> roleOrgList);
	
	/**
	 * 通过角色id删除角色菜单
	 * @param roleId
	 * @return
	 */
	public Boolean deleteRoleOrgByRoleId(Integer roleId);
	
	/**
	 * 组合条件删除角色菜单
	 * @param params
	 * @return
	 */
	public Boolean deleteRoleOrg(Map<String, Object> params);
	
	/**
	 * 获取用户角色菜单
	 * @param params
	 * @return
	 */
	public List<OrgVo> getUserRoleOrg(Map<String, Object> params);
	
}
