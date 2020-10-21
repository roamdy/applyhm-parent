package com.applyhm.dc.sys.dao.impl;

import java.util.List;
import java.util.Map;

import com.applyhm.dc.sys.search.UserSearch;
import org.springframework.stereotype.Repository;

import com.applyhm.core.frame.dao.impl.BaseDaoImpl;
import com.applyhm.dc.sys.dao.UserDao;
import com.applyhm.dc.sys.po.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, UserSearch> implements UserDao {

	@Override
	public Boolean batchInsertRoleUser(List<Map<String, Object>> roleUserList) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("batchInsertRoleUser"), roleUserList);
		if(rows > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean insertRoleUser(User user) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("insertUserRole"), user);
		if(rows > 0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean deleteRoleByUserId(Integer userId) {
		int rows = this.sqlSessionTemplate.insert(this.getSqlName("deleteRoleByUserId"), userId);
		if(rows > 0){
			return true;
		}else{
			return false;
		}
	}

	
}
