/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.11.v20180605
 * Generated at: 2020-10-21 07:54:47 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\r\n");
      out.write("<link rel=\"Shortcut Icon\" href=\"resources/images/favicon.ico\">\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-easyui-1.5.5.4/jquery.min.js\" charset=\"UTF-8\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resources/js/jquery-easyui-1.5.5.4/jquery.easyui.min.js\" charset=\"UTF-8\" type=\"text/javascript\"></script>\r\n");
      out.write("<title>登录</title>\r\n");
      out.write("<link href=\"");
      out.print(request.getContextPath());
      out.write("/resources/css/login.css\" rel=\"stylesheet\">\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t$(function(){   \r\n");
      out.write("\t\t $('#loginErrorID').hide();\r\n");
      out.write("\t  \t $('#kaptchaImage').click(function () {//生成验证码  \r\n");
      out.write("\t \t \t$(this).hide().attr('src', '");
      out.print(request.getContextPath());
      out.write("/captchaImage?' + Math.floor(Math.random()*100)).fadeIn(); \r\n");
      out.write("\t  \t })     \r\n");
      out.write("\t \t $(window).keydown(function(event){\r\n");
      out.write("\t\t\t  if(event.keyCode == 13) {//enter快捷键\r\n");
      out.write("\t\t\t  \t\tlogin();\r\n");
      out.write("\t\t\t  }\r\n");
      out.write("\t\t });\r\n");
      out.write("\t \t$(\"#userName\").focus();\r\n");
      out.write("\t});  \r\n");
      out.write("\t\r\n");
      out.write("\tfunction login(){\r\n");
      out.write("\t\tvar userName = $(\"#userName\").val();\r\n");
      out.write("\t\tif(userName ==\"\" || $(\"#password\").val()==\"\" || $(\"#validateCode\").val()==\"\"){\r\n");
      out.write("\t\t\t $(\"#lblMsg\").text(\"用户名，密码，验证码不能为空\");\r\n");
      out.write("\t\t\t $('#loginErrorID').show();\r\n");
      out.write("\t\t\t return;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t$(\"#login_form\").find(\":submit\").prop(\"disabled\", true); \r\n");
      out.write("\t\t$(\"#login_form\").form('submit',{\r\n");
      out.write("    \t\t type:'post',\r\n");
      out.write("    \t\t url:'");
      out.print(request.getContextPath());
      out.write("/sys/user/admin/login',\r\n");
      out.write("    \t\t success:function(responseData){\r\n");
      out.write("    \t\t\t if(responseData){\r\n");
      out.write("    \t\t\t\tvar data = $.parseJSON(responseData);\r\n");
      out.write("    \t\t\t\tif(!data.success){\r\n");
      out.write("    \t\t\t\t\t$(\"#login_form\").find(\":submit\").prop(\"disabled\", false); \r\n");
      out.write("    \t\t\t\t\t$('#loginErrorID').show();\r\n");
      out.write("    \t\t\t\t\t$(\"#lblMsg\").text(data.message);\r\n");
      out.write("    \t\t\t\t\treturn;\r\n");
      out.write("    \t\t\t\t}\r\n");
      out.write("    \t\t\t\tif($(\"#rememberme\").attr(\"checked\")){\r\n");
      out.write("    \t\t\t\t\tsetCookie('qbsusername',$(\"#userName\").val(),365);\r\n");
      out.write("    \t\t\t\t}\r\n");
      out.write("    \t\t\t\twindow.location.href ='");
      out.print(request.getContextPath());
      out.write("' + data.message;\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t});\r\n");
      out.write("\t};\r\n");
      out.write("\t\r\n");
      out.write("\tfunction getCookie(c_name) {\r\n");
      out.write("\t\tif (document.cookie.length > 0) {\r\n");
      out.write("\t\t\tc_start = document.cookie.indexOf(c_name + \"=\")\r\n");
      out.write("\t\t\tif (c_start != -1) {\r\n");
      out.write("\t\t\t\tc_start = c_start + c_name.length + 1\r\n");
      out.write("\t\t\t\tc_end = document.cookie.indexOf(\";\", c_start)\r\n");
      out.write("\t\t\t\tif (c_end == -1) {\r\n");
      out.write("\t\t\t\t\tc_end = document.cookie.length\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t\treturn unescape(document.cookie.substring(c_start, c_end))\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\treturn \"\"\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction setCookie(c_name, value, expiredays) {\r\n");
      out.write("\t\tvar exdate = new Date()\r\n");
      out.write("\t\texdate.setDate(exdate.getDate() + expiredays)\r\n");
      out.write("\t\tdocument.cookie = c_name + \"=\" + escape(value) + ((expiredays == null) ? \"\" : \";expires=\" + exdate.toGMTString())\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body class=\"app flex-row align-items-center\">\r\n");
      out.write("\t<div class=\"container\">\r\n");
      out.write("\t\t<div class=\"row justify-content-center\">\r\n");
      out.write("\t\t\t<div class=\"col-md-8\">\r\n");
      out.write("\t\t\t\t<div class=\"card-group\">\r\n");
      out.write("\t\t\t\t\t<div class=\"card p-4\">\r\n");
      out.write("\t\t\t\t\t\t<form class=\"form-signin\" id=\"login_form\" method=\"post\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"card-body\">\r\n");
      out.write("\t\t\t\t\t\t\t<h2>登录</h2>\r\n");
      out.write("\t\t\t\t\t\t\t<p id=\"loginErrorID\" class=\"text-muted\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<span id=\"lblMsg\" style=\"color:red;\"></span>\r\n");
      out.write("\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"input-group mb-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"input-group-prepend\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<span class=\"input-group-text\"> \r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t用户名\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input id=\"userName\" name=\"userName\" type=\"text\" class=\"form-control\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-12\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"input-group mb-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<div class=\"input-group-prepend\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t<span class=\"input-group-text\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t\t密 &nbsp;&nbsp; 码\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<input type=\"password\" id=\"password\" name=\"pwd\" class=\"form-control\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-7\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"input-group mb-3\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<div class=\"input-group-prepend\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t<span class=\"input-group-text\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t\t验证码\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input id=\"validateCode\" name=\"validateCode\" type=\"text\" class=\"form-control\">\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-5\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<img src=\"");
      out.print(request.getContextPath());
      out.write("/captchaImage\" height=\"65%\" width=\"100%\" id=\"kaptchaImage\"/>  \r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-6\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<button type=\"button\" class=\"btn btn-primary px-4\" onclick=\"login()\">登录</button>\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"col-6 text-right\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</form>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"card text-white bg-primary py-5 d-md-down-none\" style=\"width: 44%\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"card-body text-center\">\r\n");
      out.write("\t\t\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<h2>宣言·远景·使命</h2>\r\n");
      out.write("\t\t\t\t\t\t\t\t<p class=\"text-left\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t企业精神，品质第一。<br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t品质第一，客户至上。 <br>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t顾客至上，改革求实。 <br>\r\n");
      out.write("\t\t\t\t\t\t\t\t</p>\r\n");
      out.write("\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}