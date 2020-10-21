package com.applyhm.dc.sys.controller;

import java.util.List;

import com.applyhm.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.applyhm.utils.DateUtil;
import com.applyhm.core.easyui.EasyuiDataGridJson;
import com.applyhm.core.frame.controller.BaseController;
import com.applyhm.core.frame.domain.Result;
import com.applyhm.core.frame.domain.ResultCode;
import com.applyhm.dc.sys.search.OrgUserSearch;
import com.applyhm.dc.sys.service.OrgUserService;
import com.applyhm.dc.sys.service.UserService;
import com.applyhm.dc.sys.vo.OrgUserVo;

@Controller
@RequestMapping("/sys/orgUser")
public class OrgUserController extends BaseController {
	
	@Autowired
	private OrgUserService orgUserService;     //组织机构用户服务
	@Autowired
	private UserService userService;           //组织机构用户服务
	
	
	/**
	 * 后台-首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/orgUser/index";
	}
	
	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(OrgUserSearch orgUserSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
			orgUserSearch.setIsPage(true);
			List<OrgUserVo> list = this.orgUserService.getList(orgUserSearch);
			result.setTotal(this.orgUserService.getRowCount(orgUserSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 后台-添加页面
	 * @param orgUserVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(OrgUserVo orgUserVo) {
		this.request.setAttribute("orgUserVo", orgUserVo);
		this.request.setAttribute("flag", "add");
		return "/jsp/sys/admin/orgUser/add";
	}

	/**
	 * 后台-添加保存
	 * @param orgUserVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	public Result add(OrgUserVo orgUserVo) {
		Result result = new Result();
		try {
			if(StringUtils.isNotEmpty(orgUserVo.getBirthdayStr())){
				orgUserVo.setBirthday(DateUtil.format(orgUserVo.getBirthdayStr(), "yyyy-MM-dd"));
			}
			result = this.orgUserService.addOrgUser(orgUserVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(Integer id) {
		if (id != null) {
			OrgUserVo orgUserVo = this.orgUserService.getById(id);
			if(orgUserVo.getBirthday() != null){
				orgUserVo.setBirthdayStr(DateUtil.format(orgUserVo.getBirthday(), true));
			}
			this.request.setAttribute("orgUserVo", orgUserVo);
		}
		this.request.setAttribute("flag", "edit");
		return "/jsp/sys/admin/orgUser/add";
	}
	
	/**
	 * 后台-编辑保存
	 * @param orgUserVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	public Result update(OrgUserVo orgUserVo) {
		Result result = new Result();
		try {
			if(StringUtils.isNotEmpty(orgUserVo.getBirthdayStr())){
				orgUserVo.setBirthday(DateUtil.format(orgUserVo.getBirthdayStr(), "yyyy-MM-dd"));
			}
			result = this.orgUserService.editOrgUser(orgUserVo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	
}
