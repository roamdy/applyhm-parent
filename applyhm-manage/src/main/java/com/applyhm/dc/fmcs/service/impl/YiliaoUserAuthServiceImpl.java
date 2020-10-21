package com.applyhm.dc.fmcs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applyhm.core.frame.dao.BaseDao;
import com.applyhm.core.frame.service.impl.BaseServiceImpl;
import com.applyhm.dc.fmcs.dao.YiliaoUserAuthDao;
import com.applyhm.dc.fmcs.po.YiliaoUserAuth;
import com.applyhm.dc.fmcs.search.YiliaoUserAuthSearch;
import com.applyhm.dc.fmcs.service.YiliaoUserAuthService;

@Service
public class YiliaoUserAuthServiceImpl  extends BaseServiceImpl<YiliaoUserAuth, YiliaoUserAuthSearch> implements YiliaoUserAuthService {

	@Autowired
	private YiliaoUserAuthDao YiliaoUserAuthDao;

	@Override
	protected BaseDao<YiliaoUserAuth, YiliaoUserAuthSearch> getBaseDao() {
		return YiliaoUserAuthDao;
	}
}
