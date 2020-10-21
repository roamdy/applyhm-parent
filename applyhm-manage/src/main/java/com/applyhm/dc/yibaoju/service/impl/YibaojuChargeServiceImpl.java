package com.applyhm.dc.yibaoju.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.applyhm.core.frame.dao.BaseDao;
import com.applyhm.core.frame.service.impl.BaseServiceImpl;
import com.applyhm.dc.yibaoju.dao.YibaojuChargeDao;
import com.applyhm.dc.yibaoju.po.YibaojuCharge;
import com.applyhm.dc.yibaoju.search.YibaojuChargeSearch;
import com.applyhm.dc.yibaoju.service.YibaojuChargeService;

@Service
public class YibaojuChargeServiceImpl  extends BaseServiceImpl<YibaojuCharge, YibaojuChargeSearch> implements YibaojuChargeService {

	@Autowired
	private YibaojuChargeDao yibaojuChargeDao;

	@Override
	protected BaseDao<YibaojuCharge, YibaojuChargeSearch> getBaseDao() {
		return yibaojuChargeDao;
	}
}
