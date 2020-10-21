package com.applyhm.dc.danwei.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applyhm.core.frame.dao.BaseDao;
import com.applyhm.core.frame.service.impl.BaseServiceImpl;
import com.applyhm.dc.danwei.dao.DanweiDao;
import com.applyhm.dc.danwei.po.Danwei;
import com.applyhm.dc.danwei.search.DanweiSearch;
import com.applyhm.dc.danwei.service.DanweiService;


@Service
public class DanweiServiceImpl extends BaseServiceImpl<Danwei,DanweiSearch> implements DanweiService {

	@Autowired
	private DanweiDao danweiDao;

	@Override
	protected BaseDao<Danwei,DanweiSearch> getBaseDao() {
		return danweiDao;
	}


}
