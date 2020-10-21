package com.applyhm.dc.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.applyhm.utils.DateUtil;
import com.applyhm.utils.MD5Encoder;
import com.applyhm.core.frame.controller.BaseController;
import com.applyhm.core.frame.domain.Result;
import com.applyhm.core.frame.domain.ResultCode;
import com.applyhm.dc.base.GeneralConstant.UserStatus;
import com.applyhm.dc.base.GeneralConstant.UserType;
import com.applyhm.dc.sys.common.SessionInfo;
import com.applyhm.dc.sys.common.SystemConfig;
import com.applyhm.dc.sys.po.OrgUser;
import com.applyhm.dc.sys.search.UserSearch;
import com.applyhm.dc.sys.service.OrgUserService;
import com.applyhm.dc.sys.service.RoleService;
import com.applyhm.dc.sys.service.UserService;
import com.applyhm.dc.sys.vo.OrgUserVo;
import com.applyhm.dc.sys.vo.RoleVo;
import com.applyhm.dc.sys.vo.UserVo;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;           //用户服务
	@Autowired
	private OrgUserService orgUserService;     //机构用户服务
	@Autowired
	private RoleService roleService;           //角色服务

	/**
	 * 后台-系统用户登录
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
	@ResponseBody
	public Result adminLogin(UserVo userVo){
		Result result = new Result();
		try{
			UserSearch userSearch = new UserSearch();
			userSearch.setUserName(userVo.getUserName().trim());
			List<UserVo> list = this.userService.getList(userSearch);
			if(list == null || list.size() == 0){
				result.setError(ResultCode.CODE_STATE_4006, "该用户名不存在");
				return result;
			}
			UserVo loginUser = list.get(0);
			if(UserStatus.DISABLE == loginUser.getStatus()){
				result.setError(ResultCode.CODE_STATE_4006, "该账号已经被禁用，启用请联系管理员");
				return result;
			}
			if(!loginUser.getPwd().trim().equals(MD5Encoder.encodeByMD5(userVo.getPwd().trim()))){
				result.setError(ResultCode.CODE_STATE_4006, "密码不正确");
				return result;
			}
			String code = (String)this.request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(!userVo.getValidateCode().toUpperCase().equals(code.toUpperCase())){
				result.setError(ResultCode.CODE_STATE_4006, "验证码不正确");
				return result;
			}

			this.request.getSession().setAttribute("userVo", loginUser);

			OrgUser orgUser = orgUserService.getByUserId(loginUser.getId());

			SessionInfo sessionInfo = new SessionInfo();//缓存信息
			sessionInfo.setUser(loginUser);
			sessionInfo.setOrgUser(orgUser);

			RoleVo roleVo = this.roleService.getById(loginUser.getRoleId());
			sessionInfo.setRole(roleVo);

			this.request.getSession().setAttribute("sessionInfo", sessionInfo);
			this.request.getSession().setAttribute("isLiveMode", SystemConfig.isLiveMode());
			result.setOK(ResultCode.CODE_STATE_200, "/main");
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}

	/**
	 * 后台-系统用户注销登录
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value = "/admin/loginOut", method = RequestMethod.POST)
	@ResponseBody
	public Result adminloginOut(UserVo userVo){
		Result result = new Result();
		try{
			if(this.request.getSession().getAttribute("sessionInfo") != null){
				this.request.getSession().removeAttribute("sessionInfo");
			}
			result.setOK(ResultCode.CODE_STATE_200, "注销成功");
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}

	/**
	 * 获取用户列表
	 * @param userSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public List<UserVo> getUsers(UserSearch userSearch) {
		List<UserVo> list = this.userService.getList(userSearch);
		return list;
	}

	/**
	 * 编辑个人资料页面
	 * @return
	 */
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)
	public String toEdit() {
		SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");//缓存信息
		if(sessionInfo != null && sessionInfo.getUser() != null){
			UserVo userInfo = sessionInfo.getUser();
			if( UserType.ORG_USER == userInfo.getType()){
				OrgUserVo orgUserVo = this.orgUserService.getByUserId(userInfo.getId());
				if(orgUserVo != null && orgUserVo.getBirthday() != null){
					orgUserVo.setBirthdayStr(DateUtil.format(orgUserVo.getBirthday(), true));
				}
				this.request.setAttribute("orgUserVo",orgUserVo);
				this.request.setAttribute("type",userInfo.getType());
			}
		}
		return "/jsp/sys/admin/user/add";
	}

	/**
	 * 编辑个人资料保存
	 * @param orgUserVo
	 * @param userType
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(OrgUserVo orgUserVo, Integer userType){
		Result result = new Result();
		try{
			Boolean flag = false;
			if( UserType.ORG_USER == userType){
				flag = this.orgUserService.edit(orgUserVo);
			}
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/admin/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public Result changeStatus(UserVo userVo){
		Result result = new Result();
		try{
			Boolean flag = this.userService.edit(userVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}

	/**
	 * 跳转到修改密码页面
	 * @return
	 */
	@RequestMapping(value = "/toEditPwd", method = RequestMethod.GET)
	public String toEditPwd() {
		return "/jsp/sys/admin/user/editPassWord";
	}

	/**
	 * 修改密码
	 * @param oldPassWord
	 * @param newPassWord
	 * @param secondPassWord
	 * @return Result
	 */
	@RequestMapping(value = "/editPassWord", method = RequestMethod.POST)
	@ResponseBody
	public Result editPassWord(String oldPassWord, String newPassWord,String secondPassWord){
		Result result = new Result();
		try{
			UserVo currUser = null;
			SessionInfo sessionInfo = (SessionInfo) this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo!=null){
				currUser = sessionInfo.getUser();
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "你还没登陆或超时，请重新登陆");
				return result;
			}
			if(!secondPassWord.equals(newPassWord)){
				result.setError(ResultCode.CODE_STATE_4006, "确认密码错误");
				return result;
			}
			if(!MD5Encoder.encodeByMD5(oldPassWord).equals(currUser.getPwd())){
				result.setError(ResultCode.CODE_STATE_4006, "旧密码错误");
				return result;
			}
			UserVo user = new UserVo();
			user.setId(currUser.getId());
			user.setPwd(MD5Encoder.encodeByMD5(newPassWord));
			boolean flag = this.userService.edit(user);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "修改密码成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "修改密码失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}

}
