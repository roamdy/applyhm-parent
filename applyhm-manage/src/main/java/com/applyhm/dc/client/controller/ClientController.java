package com.applyhm.dc.client.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.applyhm.utils.DateUtil2;
import com.applyhm.utils.Log4jUtil;
import com.applyhm.utils.QiniuUtils;
import com.applyhm.utils.QiniuUtils.DomainToBucket;
import com.applyhm.utils.ResponseUtil;
import com.applyhm.core.frame.controller.BaseController;
import com.applyhm.core.frame.domain.Result;
import com.applyhm.core.frame.domain.ResultCode;
import com.applyhm.dc.fmcs.po.YiliaoUserAuth;
import com.applyhm.dc.fmcs.service.YiliaoUserAuthService;
import com.applyhm.dc.fmcs.vo.YiliaoUserAuthVo;
import com.applyhm.dc.yibaoju.service.YibaojuChargeService;
import com.applyhm.dc.yibaoju.vo.YibaojuChargeVo;
import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;

/**
 * 客户端控制器
 */
@Controller
@RequestMapping("/client")
public class ClientController extends BaseController {

    @Autowired
    private YiliaoUserAuthService yiliaoUserAuthService;

    @Autowired
    private YibaojuChargeService yibaojuChargeService;

	/**
	 * app 手机号注册登录
	 */
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	@ResponseBody
	public Object login(@RequestParam("tel") String tel, @RequestParam("code") String pwd) {
	    //进行验证 code
	    YiliaoUserAuth userAuth = this.yiliaoUserAuthService.getById(tel);
	    if(null != userAuth) {
	        JSONObject json = userAuth.toJsonObject();
	        json.put("isLogin", true);
	        return ResponseUtil.succ(json.toJSONString());
	    }
	    userAuth = new YiliaoUserAuth();
	    userAuth.setTel(tel);
	    this.yiliaoUserAuthService.add(userAuth);
	    userAuth = this.yiliaoUserAuthService.getById(tel);
	    return ResponseUtil.succ(userAuth.toJsonObject().toJSONString());
	}

	/**
     * 手机号状态同步
     */
    @RequestMapping(value = "/rsync", method=RequestMethod.GET)
    @ResponseBody
    public Object login(@RequestParam("tel") String tel) {
        //进行验证 code
        YiliaoUserAuth userAuth = this.yiliaoUserAuthService.getById(tel);
        if(null != userAuth) {
            JSONObject json = userAuth.toJsonObject();
            json.put("isLogin", true);
            return ResponseUtil.succ(json.toString());
        }
        userAuth = new YiliaoUserAuth();
        userAuth.setTel(tel);
        this.yiliaoUserAuthService.add(userAuth);
        userAuth = this.yiliaoUserAuthService.getById(tel);
        return ResponseUtil.succ(userAuth.toJsonObject().toString());
    }

    // 百度云信息
    private String APP_ID = "11446480";
    private String API_KEY = "2TGbSunMbm";
    private String SECRET_KEY = "AmrnHcS8GbRbsaglRureVnxHpD";

    private void aiRegister(String imageUrl, String tel) {
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", tel);
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");

        String image = imageUrl;
        String imageType = "URL";
        String groupId = "";
        String userId = tel;
        // 人脸删除
        org.json.JSONObject res = client.deleteUser(groupId, userId, options);
        Log4jUtil.info("人脸删除结果="+res.toString(2));
        // 人脸注册
        res = client.addUser(image, imageType, groupId, userId, options);
        Log4jUtil.info("人脸添加结果="+res.toString(2));
    }

    /**
     * 用户提交认证照片接口
     */
    @RequestMapping(value = "/userAuth", method = RequestMethod.POST)
    @ResponseBody
    public Result add(@RequestBody YiliaoUserAuthVo yiliaoUserAuthVo) {
        Result result = new Result();
        try{
            aiRegister(yiliaoUserAuthVo.getOrignImg(), yiliaoUserAuthVo.getTel());
            Boolean flag = this.yiliaoUserAuthService.add(yiliaoUserAuthVo);
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
     * 上传文件
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Result result = new Result();
        String bucket =  "bjqlds";
        if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
            result.setError(ResultCode.CODE_STATE_4006, "找不到对应的资源空间，请联系管理");
            return result;
        }
        byte[] files = null; //上传的文件
        int statusCode = 0;  //上传结果code
        String fileName = null;//七牛云存放文件名
        //1、获取上传的文件
        long startTime=System.currentTimeMillis();   //获取开始时间
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            Iterator<String> ite = multiRequest.getFileNames();
            while(ite.hasNext()){
                MultipartFile file = multiRequest.getFile(ite.next());
                if(file!=null){
                    try {
                         files = file.getBytes();
                         fileName = bucket +"-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ file.getOriginalFilename();
                         statusCode = QiniuUtils.uploadFile(files, fileName,bucket,QiniuUtils.UPLOAD_SIMPLE);
                         break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("上传文件共使用时间："+(endTime-startTime));
        if(statusCode == 200){//成功：返回图片存放地址
            String imageUrl = DomainToBucket.DomainToBucketMap.get(bucket)+fileName;

            result.setData(imageUrl);
            result.setSuccess(true);
            result.setOK(ResultCode.CODE_STATE_200, "上传成功");
        }else{//失败：返回失败信息
            result.setError(ResultCode.CODE_STATE_4006, "上传失败");
        }
        return result;
    }


    /**
     * 上传文件
     */
    @RequestMapping("/faceAuth")
    @ResponseBody
    public Result faceAuth(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Result result = new Result();
        String bucket =  "bjqlds";
        if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
            result.setError(ResultCode.CODE_STATE_4006, "找不到对应的资源空间，请联系管理");
            return result;
        }
        byte[] files = null; //上传的文件
        int statusCode = 0;  //上传结果code
        String fileName = null;//七牛云存放文件名
        //1、获取上传的文件
        long startTime=System.currentTimeMillis();   //获取开始时间
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart(request);
        String tel = multipartRequest.getParameter("tel");
        if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            Iterator<String> ite = multiRequest.getFileNames();
            while(ite.hasNext()){
                MultipartFile file = multiRequest.getFile(ite.next());
                if(file!=null){
                    try {
                         files = file.getBytes();
                         fileName = bucket +"-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ file.getOriginalFilename();
                         statusCode = QiniuUtils.uploadFile(files, fileName,bucket,QiniuUtils.UPLOAD_SIMPLE);
                         break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        long endTime=System.currentTimeMillis(); //获取结束时间
        System.out.println("上传文件共使用时间："+(endTime-startTime));
        if(statusCode == 200){//成功：返回图片存放地址
            String imageUrl = DomainToBucket.DomainToBucketMap.get(bucket)+fileName;
            String resultJson = faceAuth(imageUrl, tel);
            result.setData(resultJson);
            result.setSuccess(true);
            result.setOK(ResultCode.CODE_STATE_200, "上传成功");
        }else{//失败：返回失败信息
            result.setError(ResultCode.CODE_STATE_4006, "上传失败");
        }
        return result;
    }

    /**
     * 进行人脸比对
     */
    private String faceAuth(String imageUrl, String tel) {
        YiliaoUserAuth userAuth = this.yiliaoUserAuthService.getById(tel);
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        String image1 = userAuth.getOrignImg();
        String image2 = imageUrl;
        // image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
        MatchRequest req1 = new MatchRequest(image1, "URL");
        MatchRequest req2 = new MatchRequest(image2, "URL");
        ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
        requests.add(req1);
        requests.add(req2);

        org.json.JSONObject res = client.match(requests);
        Log4jUtil.info("人脸识别对比 form="+image1+"; to="+image2);
        Log4jUtil.info("人脸识别结果="+res.toString(2));
        return res.toString();
    }


    /**
     * 用户提交认证照片接口
     */
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    @ResponseBody
    public Result form(@RequestBody YibaojuChargeVo yibaojuChargeVo) {
        Result result = new Result();
        try{
            String cTime = DateUtil2.getCurrentLongDateTime();
            YiliaoUserAuth userAuth = this.yiliaoUserAuthService.getById(yibaojuChargeVo.getTel());
//          initConfigVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
//          initConfigVo.setCreateDate(cTime);
//          initConfigVo.setUpdateDate(cTime);
            yibaojuChargeVo.setCreateTime(cTime);
            yibaojuChargeVo.setOwnerDanwei(userAuth.getYibaojuId());
            yibaojuChargeVo.setHospital(userAuth.getHospitalId().toString());

            Boolean flag = this.yibaojuChargeService.add(yibaojuChargeVo);
            if(flag){
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

}
