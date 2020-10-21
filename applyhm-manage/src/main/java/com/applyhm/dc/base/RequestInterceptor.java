package com.applyhm.dc.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.applyhm.core.utils.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.applyhm.core.frame.domain.ResultCode;
import com.applyhm.dc.sys.common.SessionInfo;

public class RequestInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

	// 这些请求不拦截
    private static final Set<String> UNFILTER_URI = new HashSet<>();
    // 这些开头的请求不拦截
    private static final Set<String> UNFILTER_URI_STARTWITH = new HashSet<>();

    static {
    	 //不拦截：完全匹配的请求
    	 UNFILTER_URI.add("/captchaImage");				            //获取验证码

    	 UNFILTER_URI.add("/index");
         UNFILTER_URI.add("/north");       				            //首页-区域
         UNFILTER_URI.add("/west");        				            //首页-区域
         UNFILTER_URI.add("/center");      				            //首页-区域
         UNFILTER_URI.add("/south");       				            //首页-区域
         UNFILTER_URI.add("/home");        				            //首页-区域
         UNFILTER_URI.add("/sys/user/admin/login");                 //后台-登录
         UNFILTER_URI.add("/sys/user/admin/loginOut");              //后台-注销
    	 UNFILTER_URI.add("/sys/user/getUsers");		            //获取用户列表
    	 UNFILTER_URI.add("/sys/fileHandle/editorUploadImg");		//获取用户列表
    	 UNFILTER_URI.add("/sys/fileHandle/upload");		        //上传图片和文件

    	 //不拦截：开头匹配的请求
    	 UNFILTER_URI_STARTWITH.add("/resources");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/static");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/wx/frame");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/pay");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/general");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/client");//app

    }

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {

	}

    /**
     * 拦截所有请求
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//系统初始化
		//SystemInitializeJob.start();
		String servletPath = request.getServletPath();  //获取访问链接
		//开始匹配
		if (unsign(servletPath)){
		    return true;
		}
		//完全匹配
		if (unfilter(servletPath)){
		    return true;
		}
		String clientId = request.getParameter("clientId"); //前端请求标识
		if(StringUtils.isNotEmpty(clientId)){//来自客户端的判断
			if("-1".equals(clientId)){
				this.loginOutResponse(response);
			}else{
				return true;
			}
		}else{
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				//来自后台的判断
	        	response.sendRedirect(request.getContextPath() + "/index.jsp");
				return false;
			}
		}
        return true;
	}

	/**
	 *
	 * @param msgCode   对应消息码
	 * @param msg       返回消息
	 * @param request   请求对象
	 * @param response  响应对象
	 * @throws ServletException
	 * @throws IOException
	 */
	 private void forward(Integer msgCode, String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.setAttribute("msg", msg);
	        if (msgCode == ResultCode.CODE_STATE_4002) {
	            request.getRequestDispatcher("/login.jsp").forward(request, response);
	        } else {
	            request.getRequestDispatcher("/jsp/error/authMsg.jsp").forward(request, response);
	        }
	 }

	 /**
     * 这些不拦截
     *
     * @param servletPath
     * @return
     */
    private boolean unfilter(String servletPath) {
        if (UNFILTER_URI.contains(servletPath)) {
            return true;
        }
        return false;
    }

    private boolean unsign(String servletPath) {
        for (String path : UNFILTER_URI_STARTWITH) {
            if (servletPath.startsWith(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 前端页面登陆失效响应
     * @param response
     * @throws Exception
     */
    private void loginOutResponse(HttpServletResponse response) throws Exception{
    	//客户端无法跳转，返回json信息，由前端页面请求控制
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json");
		PrintWriter out =response.getWriter();//提取出来以后报错，所以每次使用都生成一个
		try{
		JSONObject obj = new JSONObject();
		obj.put("success", false);
		obj.put("logout", true);
		obj.put("message", "登录超时，请重新登录");
		out.println(obj.toJSONString());
		out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
    }


}
