package com.applyhm.dc.danwei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.applyhm.core.easyui.EasyuiDataGridJson;
import com.applyhm.core.frame.controller.BaseController;
import com.applyhm.core.frame.domain.Result;
import com.applyhm.core.frame.domain.ResultCode;
import com.applyhm.dc.danwei.search.DanweiSearch;
import com.applyhm.dc.danwei.service.DanweiService;
import com.applyhm.dc.danwei.vo.DanweiVo;

/**
 * 单位管理
 */
@Controller
@RequestMapping("/danwei/danwei")
public class DanweiController extends BaseController {

	@Autowired
	private DanweiService danweiService;


	/**
	 * 后台-首页
	 *
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
	public String toIndex() {
		return "/jsp/sys/admin/danwei/index";
	}

	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(DanweiSearch danweiSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
		    danweiSearch.setIsPage(true);
			List<DanweiVo> list = this.danweiService.getList(danweiSearch);
			result.setTotal(this.danweiService.getRowCount(danweiSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据条件获取列表信息
	 * @param danweiSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public List<DanweiVo> getList(DanweiSearch danweiSearch) {
		List<DanweiVo> list = this.danweiService.getList(danweiSearch);
		return list;
	}

	/**
	 * 后台-添加页面
	 * @param danweiVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(DanweiVo danweiVo) {
		this.request.setAttribute("danweiVo",danweiVo);
		return "/jsp/sys/admin/danwei/add";
	}

	/**
	 * 后台-添加保存
	 * @param danweiVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(DanweiVo danweiVo) {
		Result result = new Result();
		try{
			Boolean flag = this.danweiService.add(danweiVo);
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
	 * 后台-编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/toEdit", method = RequestMethod.GET)
	public String toEdit(Integer id) {
		if(id != null){
		    DanweiVo danweiVo = this.danweiService.getById(id);
			this.request.setAttribute("danweiVo",danweiVo);
		}
		return "/jsp/sys/admin/danwei/add";
	}

	/**
	 * 后台-编辑保存
	 * @param danweiVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(DanweiVo danweiVo) {
		Result result = new Result();
		try {
			Boolean flag = this.danweiService.edit(danweiVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}


	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/admin/deleteById", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteById(Integer id) {
		Result result = new Result();
		try{
			Boolean flag = this.danweiService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}


}
