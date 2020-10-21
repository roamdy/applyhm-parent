package com.applyhm.dc.yibaoju.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.applyhm.utils.DateUtil2;
import com.applyhm.utils.QiniuUtils;
import com.applyhm.utils.QiniuUtils.DomainToBucket;
import com.applyhm.core.easyui.EasyuiDataGridJson;
import com.applyhm.core.frame.controller.BaseController;
import com.applyhm.core.frame.domain.Result;
import com.applyhm.core.frame.domain.ResultCode;
import com.applyhm.core.utils.StringUtils;
import com.applyhm.dc.danwei.search.DanweiSearch;
import com.applyhm.dc.danwei.service.DanweiService;
import com.applyhm.dc.danwei.vo.DanweiVo;
import com.applyhm.dc.sys.common.SessionInfo;
import com.applyhm.dc.sys.search.UserSearch;
import com.applyhm.dc.sys.service.UserService;
import com.applyhm.dc.sys.vo.UserVo;
import com.applyhm.dc.yibaoju.search.YibaojuChargeSearch;
import com.applyhm.dc.yibaoju.service.YibaojuChargeService;
import com.applyhm.dc.yibaoju.vo.YibaojuChargeVo;
import com.applyhm.util.PrintUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Controller
@RequestMapping("/yibaoju/charge")
public class YibaojuChargeController extends BaseController {

	protected static final Logger logger = Logger.getLogger(YibaojuChargeController.class);

	@Autowired
	private YibaojuChargeService yibaojuChargeService;

	@Autowired
	private UserService userService;

	@Autowired
	private DanweiService danweiService;

	@Autowired
	private SimpMessageSendingOperations simpMessageSendingOperations;

	/**
	 * 后台-首页
	 * @return
	 */
	@RequestMapping(value = "/admin/toIndexConfirmed", method = RequestMethod.GET)
	public String toIndexConfirmed() {
		return "/jsp/yibaoju/admin/charge/indexConfirmed";
	}

	/**
     * 后台-首页
     * @return
     */
    @RequestMapping(value = "/admin/toIndexNoConfirmed", method = RequestMethod.GET)
    public String toIndexNoConfirmed() {
        return "/jsp/yibaoju/admin/charge/indexNoConfirmed";
    }

    /**
     * 后台-首页
     * @return
     */
    @RequestMapping(value = "/admin/toIndex", method = RequestMethod.GET)
    public String toIndex() {
        return "/jsp/yibaoju/admin/charge/index";
    }

    /**
     * 后台-首页
     * @return
     */
    @RequestMapping(value = "/admin/toIndexManager", method = RequestMethod.GET)
    public String toIndexManager() {
        return "/jsp/yibaoju/admin/charge/indexManager";
    }

    /**
     * 后台-首页
     * @return
     */
    @RequestMapping(value = "/admin/toIndexHospital", method = RequestMethod.GET)
    public String toIndexHospital() {
        return "/jsp/yibaoju/admin/charge/indexHospital";
    }

	/**
	 * 获取列表
	 * @return list
	 */
	@RequestMapping(value = "/admin/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyuiDataGridJson list(YibaojuChargeSearch yibaojuChargeSearch){
		EasyuiDataGridJson result = new EasyuiDataGridJson();//页面DataGrid结果集
		try{
		    yibaojuChargeSearch.setIsPage(true);
		    List<YibaojuChargeVo> list = null;
		    //按照角色区分列表
		    SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
            String roleName = sessionInfo.getRole().getRoleCode();
            Integer danweiId = sessionInfo.getUser().getDanweiId();

            if( "yibaoju".equals(roleName) ) {
                yibaojuChargeSearch.setOwnerDanwei_db(danweiId.toString());
                yibaojuChargeSearch.setHospital_db(null);
                list = this.yibaojuChargeService.getList(yibaojuChargeSearch);
            } else if("hospital".equals(roleName)) {
                yibaojuChargeSearch.setOwnerDanwei_db(null);
                yibaojuChargeSearch.setHospital_db(danweiId.toString());
                list = this.yibaojuChargeService.getList(yibaojuChargeSearch);
                for(YibaojuChargeVo vo:list) {
                    vo.setChargeNum(null);
                }
            } else {
                yibaojuChargeSearch.setOwnerDanwei_db(null);
                yibaojuChargeSearch.setHospital_db(null);
                list = this.yibaojuChargeService.getList(yibaojuChargeSearch);
            }
			result.setTotal(this.yibaojuChargeService.getRowCount(yibaojuChargeSearch));
			result.setRows(list);
			return result;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
     * 导出列表
     * @return list
     */
    @RequestMapping(value = "/admin/exportExcel", method = RequestMethod.GET)
    @ResponseBody
    public void exportExcel(YibaojuChargeSearch yibaojuChargeSearch, HttpServletRequest request, HttpServletResponse response){
        try{
            yibaojuChargeSearch.setIsPage(false);
            yibaojuChargeSearch.setSort("charges.hospital");
            List<YibaojuChargeVo> list = null;
            //按照角色区分列表
            SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
            String roleName = sessionInfo.getRole().getRoleCode();
            Integer danweiId = sessionInfo.getUser().getDanweiId();

            if( "yibaoju".equals(roleName) ) {
                yibaojuChargeSearch.setOwnerDanwei_db(danweiId.toString());
                yibaojuChargeSearch.setHospital_db(null);
                list = this.yibaojuChargeService.getList(yibaojuChargeSearch);
            } else if("hospital".equals(roleName)) {
                yibaojuChargeSearch.setOwnerDanwei_db(null);
                yibaojuChargeSearch.setHospital_db(danweiId.toString());
                list = this.yibaojuChargeService.getList(yibaojuChargeSearch);
                for(YibaojuChargeVo vo:list) {
                    vo.setChargeNum(null);
                }
            } else {
                yibaojuChargeSearch.setOwnerDanwei_db(null);
                yibaojuChargeSearch.setHospital_db(null);
                list = this.yibaojuChargeService.getList(yibaojuChargeSearch);
            }
            response.setContentType("application/vnd.ms-excel");
            String excelName = null;
            excelName = URLEncoder.encode("导出列表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ excelName+".xls");
            response.setCharacterEncoding("utf-8");
            OutputStream out = response.getOutputStream();
            if(list != null && list.size() > 0){
                // 创建一个工作簿
                HSSFWorkbook workBook = new HSSFWorkbook();
                // 创建一个工作表
                HSSFSheet sheet = workBook.createSheet();
                // 设置表格默认列宽度为15个字节
                sheet.setDefaultColumnWidth((short)30);
                HSSFRow row = sheet.createRow(0);

                int columnIndex = 9;
                HSSFCell cell[] = new HSSFCell[columnIndex];
                for (int i = 0; i < columnIndex; i++) {
                    cell[i] = row.createCell(i);
                }
                cell[0].setCellValue("姓名");
                cell[1].setCellValue("姓名");
                cell[2].setCellValue("住院号");
                cell[3].setCellValue("病历号");
                cell[4].setCellValue("住院时间");
                cell[5].setCellValue("出院时间");
                cell[6].setCellValue("金额");
                cell[7].setCellValue("发票号");
                cell[8].setCellValue("医院");

                for(int i=0; i<list.size(); i++){
                    YibaojuChargeVo temp = list.get(i);
                    HSSFRow row1 = sheet.createRow(i+1);
                    HSSFCell cellData[] = new HSSFCell[cell.length];
                    for (int j = 0; j < cell.length; j++) {
                        cellData[j] = row1.createCell(j);
                    }
                    cellData[0].setCellValue(temp.getUserName());
                    cellData[1].setCellValue(temp.getIdCard());
                    cellData[2].setCellValue(temp.getHospitalNo());
                    cellData[3].setCellValue(temp.getSickNo());
                    cellData[4].setCellValue(temp.getInHospitalDay().substring(0, 10));
                    cellData[5].setCellValue(temp.getOutHospitalDay().substring(0,10));
                    cellData[6].setCellValue(temp.getChargeNum());
                    cellData[7].setCellValue(temp.getChargeNo());
                    cellData[8].setCellValue(temp.getHospitalName());
                }
                workBook.write(out);
                out.flush();
                out.close();
            }

        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }
    }

	/**
	 * 后台-添加页面
	 * @param initConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/toAdd", method = RequestMethod.GET)
	public String toAdd(YibaojuChargeVo yibaojuChargeVo) {
		this.request.setAttribute("yibaojuChargeVo", yibaojuChargeVo);
		return "/jsp/yibaoju/admin/charge/add";
	}

	/**
	 * 后台-添加保存
	 * @param initConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(YibaojuChargeVo yibaojuChargeVo) {
		Result result = new Result();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
//			initConfigVo.setId(UUIDUtils.create());
			String cTime = DateUtil2.getCurrentLongDateTime();
			Integer danweiId = sessionInfo.getUser().getDanweiId();
//			initConfigVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
//			initConfigVo.setCreateDate(cTime);
//			initConfigVo.setUpdateDate(cTime);
			yibaojuChargeVo.setCreateTime(cTime);
			yibaojuChargeVo.setOwnerDanwei(danweiId);

			if(null != yibaojuChargeVo.getHospital() && !StringUtils.isNumeric(yibaojuChargeVo.getHospital().toString())) {
			    DanweiSearch search = new DanweiSearch();
			    search.setName(yibaojuChargeVo.getHospital());
	            List<DanweiVo> danweiVos = danweiService.getList(search);
	            if(null == danweiVos || danweiVos.size() == 0) {
	                DanweiVo danweiVo = new DanweiVo();
	                danweiVo.setDanwei(yibaojuChargeVo.getHospital());
	                danweiVo.setRemark(yibaojuChargeVo.getHospital());
	                danweiVo.setType("hospital");
	                Boolean flag = danweiService.add(danweiVo);
	                if(flag) {
	                    yibaojuChargeVo.setHospital(danweiVo.getId().toString());
	                }
	            } else {
	                DanweiVo danweiVo = danweiVos.get(0);
	                yibaojuChargeVo.setHospital(danweiVo.getId().toString());
	            }
	        }

			Boolean flag = this.yibaojuChargeService.add(yibaojuChargeVo);
			if(flag){
			    UserSearch userSearch = new UserSearch();
	            userSearch.setDanweiId(Integer.parseInt(yibaojuChargeVo.getHospital()));
	            List<UserVo> list = this.userService.getList(userSearch);
	            for(UserVo vo:list) {
	                simpMessageSendingOperations.convertAndSendToUser(vo.getUserName(), "/message", "您收到新的记录消息");
	            }
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
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
	public String toEdit(String id) {
	    SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
        String roleName = sessionInfo.getRole().getRoleCode();
		if(id != null){
		    YibaojuChargeVo yibaojuChargeVo = this.yibaojuChargeService.getById(id);
		    //按照角色区分列表
            if( "hospital".equals(roleName) ) {
                yibaojuChargeVo.setChargeNum(null);
            }
			this.request.setAttribute("yibaojuChargeVo", yibaojuChargeVo);
		}
		if( "hospital".equals(roleName) ) {
		    return "/jsp/yibaoju/admin/charge/hospitalValidate";
		}
		return "/jsp/yibaoju/admin/charge/add";
	}

	/**
	 * 后台-编辑保存
	 * @param initConfigVo
	 * @return
	 */
	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	@ResponseBody
	public Result update(YibaojuChargeVo yibaojuChargeVo) {
		Result result = new Result();
		try {
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");

			if(null != yibaojuChargeVo.getHospital() && !StringUtils.isNumeric(yibaojuChargeVo.getHospital().toString())) {
                DanweiSearch search = new DanweiSearch();
                search.setName(yibaojuChargeVo.getHospital());
                List<DanweiVo> danweiVos = danweiService.getList(search);
                if(null == danweiVos || danweiVos.size() == 0) {
                    DanweiVo danweiVo = new DanweiVo();
                    danweiVo.setDanwei(yibaojuChargeVo.getHospital());
                    danweiVo.setRemark(yibaojuChargeVo.getHospital());
                    danweiVo.setType("hospital");
                    Boolean flag = danweiService.add(danweiVo);
                    if(flag) {
                        yibaojuChargeVo.setHospital(danweiVo.getId().toString());
                    }
                } else {
                    DanweiVo danweiVo = danweiVos.get(0);
                    yibaojuChargeVo.setHospital(danweiVo.getId().toString());
                }
            }

			Boolean flag = this.yibaojuChargeService.edit(yibaojuChargeVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}


    /**
     * 后台-验证金额
     * @param initConfigVo
     * @return
     */
    @RequestMapping(value = "/admin/updateValidate", method = RequestMethod.POST)
    @ResponseBody
    public Result updateValidate(YibaojuChargeVo yibaojuChargeVo) {
        Result result = new Result();
        try {
            YibaojuChargeVo dbVo = this.yibaojuChargeService.getById(yibaojuChargeVo.getId().toString());
            Boolean flag = (dbVo.getChargeNum().compareTo(yibaojuChargeVo.getChargeNum()) == 0);
            if(flag){
                String templatePath = "/opt/app/yiliao/baogao.pdf";
                String fileName = "baogao_"+System.currentTimeMillis()+".pdf";
                String newPDFPath = "/opt/app/yiliao/"+fileName;
                FileOutputStream fileOut = new FileOutputStream(newPDFPath);//输出流
                PdfReader reader = new PdfReader(templatePath);//读取pdf模板
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                PdfStamper stamper = new PdfStamper(reader, bos);
                //使用中文字体
                BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
                AcroFields form = stamper.getAcroFields();
                Iterator<String> it = form.getFields().keySet().iterator();
                while(it.hasNext()){
                    String name = it.next().toString();
                    form.setFieldProperty(name, "textfont", bf, null);
                    if("xieyiNo".equals(name)) {
                        form.setField(name, System.currentTimeMillis()+"");
                    }
                    if("yibaojuName".equals(name)) {
                        form.setField(name, dbVo.getYibaojuName());
                    }
                    if("createTime".equals(name)) {
                        String now = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        form.setField(name, now);//dbVo.getCreateTime().substring(0, 10)
                    }
                    if("userName".equals(name)) {
                        form.setField(name, dbVo.getUserName());
                    }
                    if("chargeNo".equals(name)) {
                        form.setField(name, dbVo.getSickNo());
                    }
                    if("hospitalName".equals(name)) {
                        form.setField(name, dbVo.getHospitalName());
                    }
                    if("hospitalTime".equals(name)) {
                        form.setField(name, dbVo.getInHospitalDay().substring(0, 10) + " - " + dbVo.getOutHospitalDay().substring(0, 10));
                    }
                    if("chargeNum".equals(name)) {
                        form.setField(name, dbVo.getChargeNum()+"");
                    }
                }
                stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
                stamper.close();

                Document doc = new Document();
                PdfCopy copy = new PdfCopy(doc, fileOut);
                doc.open();
                PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
                copy.addPage(importPage);
                doc.close();
                fileName = "bjqlds-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ new File(newPDFPath).getName();
                int statusCode = QiniuUtils.uploadFileLocal(newPDFPath, fileName, "bjqlds" , QiniuUtils.UPLOAD_SIMPLE);
                if(statusCode == 200) {
                    YibaojuChargeVo saveVo = new YibaojuChargeVo();
                    saveVo.setId(dbVo.getId());
                    saveVo.setStatus(1);
                    saveVo.setOverTime(DateUtil2.getCurrentLongDateTime());
                    saveVo.setBaogao_url(DomainToBucket.DomainToBucketMap.get("bjqlds")+fileName);
                    yibaojuChargeService.edit(saveVo);
                    result.setOK(ResultCode.CODE_STATE_200, "审核通过，生成报告成功");
                }
            }else{
                String templatePath = "/opt/app/yiliao/nobaogao.pdf";
                String fileName = "baogao_"+System.currentTimeMillis()+".pdf";
                String newPDFPath = "/opt/app/yiliao/"+fileName;
                FileOutputStream fileOut = new FileOutputStream(newPDFPath);//输出流
                PdfReader reader = new PdfReader(templatePath);//读取pdf模板
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                PdfStamper stamper = new PdfStamper(reader, bos);
                //使用中文字体
                BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
                AcroFields form = stamper.getAcroFields();
                Iterator<String> it = form.getFields().keySet().iterator();
                while(it.hasNext()){
                    String name = it.next().toString();
                    form.setFieldProperty(name, "textfont", bf, null);
                    if("xieyiNo".equals(name)) {
                        form.setField(name, System.currentTimeMillis()+"");
                    }
                    if("yibaojuName".equals(name)) {
                        form.setField(name, dbVo.getYibaojuName());
                    }
                    if("createTime".equals(name)) {
                        String now = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        form.setField(name, now);//dbVo.getCreateTime().substring(0, 10)
                    }
                    if("userName".equals(name)) {
                        form.setField(name, dbVo.getUserName());
                    }
                    if("chargeNo".equals(name)) {
                        form.setField(name, dbVo.getSickNo());
                    }
                    if("hospitalName".equals(name)) {
                        form.setField(name, dbVo.getHospitalName());
                    }
                    if("hospitalTime".equals(name)) {
                        form.setField(name, dbVo.getInHospitalDay().substring(0, 10) + " - " + dbVo.getOutHospitalDay().substring(0, 10));
                    }
                    if("chargeNum".equals(name)) {
                        form.setField(name, dbVo.getChargeNum()+"");
                    }
                    if("realNum".equals(name)) {
                        form.setField(name, yibaojuChargeVo.getChargeNum()+"");
                    }
                }
                stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
                stamper.close();

                Document doc = new Document();
                PdfCopy copy = new PdfCopy(doc, fileOut);
                doc.open();
                PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
                copy.addPage(importPage);
                doc.close();
                fileName = "bjqlds-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ new File(newPDFPath).getName();
                int statusCode = QiniuUtils.uploadFileLocal(newPDFPath, fileName, "bjqlds" , QiniuUtils.UPLOAD_SIMPLE);
                if(statusCode == 200) {
                    YibaojuChargeVo saveVo = new YibaojuChargeVo();
                    saveVo.setId(dbVo.getId());
                    saveVo.setStatus(2);
                    saveVo.setOverTime(DateUtil2.getCurrentLongDateTime());
                    saveVo.setBaogao_url(DomainToBucket.DomainToBucketMap.get("bjqlds")+fileName);
                    yibaojuChargeService.edit(saveVo);
                    result.setOK(ResultCode.CODE_STATE_200, "金额不正确，审核不通过");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setError(ResultCode.CODE_STATE_500, e.getMessage());
        }
        return result;
    }

    /**
     * 后台-通过审核
     * @param initConfigVo
     * @return
     */
    @RequestMapping(value = "/admin/doYes", method = RequestMethod.POST)
    @ResponseBody
    public Result doYes(String id) {
        Result result = new Result();
        try {
            YibaojuChargeVo dbVo = this.yibaojuChargeService.getById(id);
            String templatePath = "/opt/app/yiliao/baogao.pdf";
            String fileName = "baogao_"+System.currentTimeMillis()+".pdf";
            String newPDFPath = "/opt/app/yiliao/"+fileName;
            FileOutputStream fileOut = new FileOutputStream(newPDFPath);//输出流
            PdfReader reader = new PdfReader(templatePath);//读取pdf模板
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);
            //使用中文字体
            BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
            AcroFields form = stamper.getAcroFields();
            Iterator<String> it = form.getFields().keySet().iterator();
            while(it.hasNext()){
                String name = it.next().toString();
                form.setFieldProperty(name, "textfont", bf, null);
                if("xieyiNo".equals(name)) {
                    form.setField(name, System.currentTimeMillis()+"");
                }
                if("yibaojuName".equals(name)) {
                    form.setField(name, dbVo.getYibaojuName());
                }
                if("createTime".equals(name)) {
                    String now = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    form.setField(name, now);//dbVo.getCreateTime().substring(0, 10)
                }
                if("userName".equals(name)) {
                    form.setField(name, dbVo.getUserName());
                }
                if("chargeNo".equals(name)) {
                    form.setField(name, dbVo.getSickNo());
                }
                if("hospitalName".equals(name)) {
                    form.setField(name, dbVo.getHospitalName());
                }
                if("hospitalTime".equals(name)) {
                    form.setField(name, dbVo.getInHospitalDay().substring(0, 10) + " - " + dbVo.getOutHospitalDay().substring(0, 10));
                }
                if("chargeNum".equals(name)) {
                    form.setField(name, dbVo.getChargeNum()+"");
                }
            }
            stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, fileOut);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            fileName = "bjqlds-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ new File(newPDFPath).getName();
            int statusCode = QiniuUtils.uploadFileLocal(newPDFPath, fileName, "bjqlds" , QiniuUtils.UPLOAD_SIMPLE);
            if(statusCode == 200) {
                YibaojuChargeVo saveVo = new YibaojuChargeVo();
                saveVo.setId(dbVo.getId());
                saveVo.setStatus(1);
                saveVo.setOverTime(DateUtil2.getCurrentLongDateTime());
                saveVo.setBaogao_url(DomainToBucket.DomainToBucketMap.get("bjqlds")+fileName);
                saveVo.setPdfPath(newPDFPath);
                yibaojuChargeService.edit(saveVo);
                result.setOK(ResultCode.CODE_STATE_200, "审核通过，生成报告成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setError(ResultCode.CODE_STATE_500, e.getMessage());
        }
        return result;
    }

    /**
     * 后台-废弃
     * @param initConfigVo
     * @return
     */
    @RequestMapping(value = "/admin/doNo", method = RequestMethod.POST)
    @ResponseBody
    public Result doNo(String id) {
        Result result = new Result();
        try {
            YibaojuChargeVo dbVo = this.yibaojuChargeService.getById(id);

            String templatePath = "/opt/app/yiliao/abandon.pdf";
            String fileName = "baogao_"+System.currentTimeMillis()+".pdf";
            String newPDFPath = "/opt/app/yiliao/"+fileName;
            FileOutputStream fileOut = new FileOutputStream(newPDFPath);//输出流
            PdfReader reader = new PdfReader(templatePath);//读取pdf模板
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, bos);
            //使用中文字体
            BaseFont bf = BaseFont.createFont("STSongStd-Light",  "UniGB-UCS2-H", false);
            AcroFields form = stamper.getAcroFields();
            Iterator<String> it = form.getFields().keySet().iterator();
            while(it.hasNext()){
                String name = it.next().toString();
                form.setFieldProperty(name, "textfont", bf, null);
                if("xieyiNo".equals(name)) {
                    form.setField(name, System.currentTimeMillis()+"");
                }
                if("yibaojuName".equals(name)) {
                    form.setField(name, dbVo.getYibaojuName());
                }
                if("createTime".equals(name)) {
                    String now = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    form.setField(name, now);//dbVo.getCreateTime().substring(0, 10)
                }
                if("userName".equals(name)) {
                    form.setField(name, dbVo.getUserName());
                }
                if("chargeNo".equals(name)) {
                    form.setField(name, dbVo.getSickNo());
                }
                if("hospitalName".equals(name)) {
                    form.setField(name, dbVo.getHospitalName());
                }
                if("hospitalTime".equals(name)) {
                    form.setField(name, dbVo.getInHospitalDay().substring(0, 10) + " - " + dbVo.getOutHospitalDay().substring(0, 10));
                }
                if("chargeNum".equals(name)) {
                    form.setField(name, dbVo.getChargeNum()+"");
                }
            }
            stamper.setFormFlattening(true);//如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, fileOut);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            fileName = "bjqlds-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ new File(newPDFPath).getName();
            int statusCode = QiniuUtils.uploadFileLocal(newPDFPath, fileName, "bjqlds" , QiniuUtils.UPLOAD_SIMPLE);
            if(statusCode == 200) {
                YibaojuChargeVo saveVo = new YibaojuChargeVo();
                saveVo.setId(dbVo.getId());
                saveVo.setStatus(2);
                saveVo.setOverTime(DateUtil2.getCurrentLongDateTime());
                saveVo.setBaogao_url(DomainToBucket.DomainToBucketMap.get("bjqlds")+fileName);
                yibaojuChargeService.edit(saveVo);
                result.setOK(ResultCode.CODE_STATE_200, "该记录已废弃");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
	public Result deleteById(String id) {
		Result result = new Result();
		try{
			Boolean flag = this.yibaojuChargeService.deleteById(id);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}

	/**
     * 后台-通过审核
     * @param initConfigVo
     * @return
     */
    @RequestMapping(value = "/admin/doPrint", method = RequestMethod.POST)
    @ResponseBody
    public Result doPrint(String id) {
        Result result = new Result();
        try {
            YibaojuChargeVo dbVo = this.yibaojuChargeService.getById(id);

            if(StringUtils.isEmpty(dbVo.getPdfPath())) {
                result.setOK(ResultCode.CODE_STATE_200, "未找到打印文件，请确认已通过审核");
                return result;
            }
            YibaojuChargeVo saveVo = new YibaojuChargeVo();
            saveVo.setId(dbVo.getId());
            saveVo.setPrint_status(1);
            yibaojuChargeService.edit(saveVo);
            boolean print = PrintUtil.print(dbVo.getPdfPath());
            if(print) {
                result.setOK(ResultCode.CODE_STATE_200, "打印成功，已标记为打印");
                return result;
            }
            result.setOK(ResultCode.CODE_STATE_200, "未找到打印机，已标记为打印");
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setError(ResultCode.CODE_STATE_500, e.getMessage());
        }
        return result;
    }

}
