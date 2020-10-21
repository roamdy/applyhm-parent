package com.applyhm.dc.base;

import java.util.HashMap;
import java.util.Map;


public interface GeneralConstant {
	/**
	 * 系统用户类型
	 */
	public abstract class UserType {
		/*** 机构用户 */
		public static final int ORG_USER = 1;
		/*** 代理商用户  */
		public static final int AGENT_USER = 2;

		public static Map<Integer, String> UserTypeMap = new HashMap<Integer, String>();
		static {
			UserTypeMap.put(ORG_USER, "机构用户");
			UserTypeMap.put(AGENT_USER, "代理用户");
		}
	}

	/**
	 * 系统用户类型
	 */
	public abstract class UserStatus {
		/*** 禁用  */
		public static final int DISABLE = 0;
		/*** 启用 */
		public static final int ACTIVITY = 1;


		public static Map<Integer, String> UserStatusMap = new HashMap<Integer, String>();
		static {
			UserStatusMap.put(ACTIVITY, "启用");
			UserStatusMap.put(DISABLE, "禁用");
		}
	}



}
