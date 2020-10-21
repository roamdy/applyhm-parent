package com.applyhm.dc.sys.service.impl;

import java.util.List;

import com.applyhm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applyhm.core.frame.dao.BaseDao;
import com.applyhm.core.frame.domain.Result;
import com.applyhm.core.frame.domain.ResultCode;
import com.applyhm.core.frame.service.impl.BaseServiceImpl;
import com.applyhm.dc.sys.common.SessionInfo;
import com.applyhm.dc.sys.dao.UserDao;
import com.applyhm.dc.sys.po.User;
import com.applyhm.dc.sys.search.UserSearch;
import com.applyhm.dc.sys.service.UserService;
import com.applyhm.dc.sys.vo.UserVo;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,UserSearch> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	protected BaseDao<User,UserSearch> getBaseDao() {
		return userDao;
	}

	@Override
	public List<UserVo> getSyncGridTree(List<UserVo> list, Integer parentId) {
		return null;
	}

	@Override
	public Result addRegisterUser(UserVo userVo) {
		return null;
	}

	@Override
	public Boolean batchAddRoleUserByType(Integer userId, String companyTypeIds) {
		return null;
	}

	@Override
	public Result addChildAccount(UserVo userVo, SessionInfo sessionInfo) {
		return null;
	}

	@Override
	public Result commonValid(UserVo userVo) {
		return null;
	}

	@Override
	public Result validUserInfo(UserVo userVo) {
		Result result = new Result();
		if(StringUtils.isEmpty(userVo.getUserName())){
			result.setError(ResultCode.CODE_STATE_4006, "账号名不能为空!");
			return result;
		}
		UserSearch userSearch = new UserSearch();
		userSearch.setUserName(userVo.getUserName().trim());
		Long rows = this.getRowCount(userSearch);
		if(rows > 0){
			result.setError(ResultCode.CODE_STATE_4006, "该账号名已经存在！请使用其他账号名");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "信息有效");
		return result;
	}

	@Override
	public Boolean addUserRole(UserVo userVo) {
		return this.userDao.insertRoleUser(userVo);
	}

	@Override
	public Boolean deleteRoleByUserId(Integer userId) {
		return this.userDao.deleteRoleByUserId(userId);
	}
	

}
