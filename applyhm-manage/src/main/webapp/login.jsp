<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link rel="Shortcut Icon" href="resources/images/favicon.ico">
<script src="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.5.5.4/jquery.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.5.5.4/jquery.easyui.min.js" charset="UTF-8" type="text/javascript"></script>
<title>登录</title>
<link href="<%=request.getContextPath()%>/resources/css/login.css" rel="stylesheet">
<script type="text/javascript">
	$(function(){   
		 $('#loginErrorID').hide();
	  	 $('#kaptchaImage').click(function () {//生成验证码  
	 	 	$(this).hide().attr('src', '<%=request.getContextPath()%>/captchaImage?' + Math.floor(Math.random()*100)).fadeIn(); 
	  	 })     
	 	 $(window).keydown(function(event){
			  if(event.keyCode == 13) {//enter快捷键
			  		login();
			  }
		 });
	 	$("#userName").focus();
	});  
	
	function login(){
		var userName = $("#userName").val();
		if(userName =="" || $("#password").val()=="" || $("#validateCode").val()==""){
			 $("#lblMsg").text("用户名，密码，验证码不能为空");
			 $('#loginErrorID').show();
			 return;
		}
		$("#login_form").find(":submit").prop("disabled", true); 
		$("#login_form").form('submit',{
    		 type:'post',
    		 url:'<%=request.getContextPath()%>/sys/user/admin/login',
    		 success:function(responseData){
    			 if(responseData){
    				var data = $.parseJSON(responseData);
    				if(!data.success){
    					$("#login_form").find(":submit").prop("disabled", false); 
    					$('#loginErrorID').show();
    					$("#lblMsg").text(data.message);
    					return;
    				}
    				if($("#rememberme").attr("checked")){
    					setCookie('qbsusername',$("#userName").val(),365);
    				}
    				window.location.href ='<%=request.getContextPath()%>' + data.message;
				}
			}
		});
	};
	
	function getCookie(c_name) {
		if (document.cookie.length > 0) {
			c_start = document.cookie.indexOf(c_name + "=")
			if (c_start != -1) {
				c_start = c_start + c_name.length + 1
				c_end = document.cookie.indexOf(";", c_start)
				if (c_end == -1) {
					c_end = document.cookie.length
				}
				return unescape(document.cookie.substring(c_start, c_end))
			}
		}
		return ""
	}

	function setCookie(c_name, value, expiredays) {
		var exdate = new Date()
		exdate.setDate(exdate.getDate() + expiredays)
		document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
	}
</script>
</head>

<body class="app flex-row align-items-center">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card-group">
					<div class="card p-4">
						<form class="form-signin" id="login_form" method="post">
						<div class="card-body">
							<h2>登录</h2>
							<p id="loginErrorID" class="text-muted">
								<span id="lblMsg" style="color:red;"></span>
							</p>
							<div class="row">
								<div class="col-12">
									<div class="input-group mb-3">
										<div class="input-group-prepend">
											<span class="input-group-text"> 
												用户名
											</span>
										</div>
										<input id="userName" name="userName" type="text" class="form-control">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<div class="input-group mb-3">
										<div class="input-group-prepend">
											<span class="input-group-text">
												密 &nbsp;&nbsp; 码
											</span>
										</div>
										<input type="password" id="password" name="pwd" class="form-control">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-7">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text">
											验证码
										</span>
									</div>
									<input id="validateCode" name="validateCode" type="text" class="form-control">
								</div>
								</div>
								<div class="col-5">
									<img src="<%=request.getContextPath()%>/captchaImage" height="65%" width="100%" id="kaptchaImage"/>  
								</div>
							</div>
							<div class="row">
								<div class="col-6">
									<button type="button" class="btn btn-primary px-4" onclick="login()">登录</button>
								</div>
								<div class="col-6 text-right">
									
								</div>
							</div>
						</div>
						</form>
					</div>
					<div class="card text-white bg-primary py-5 d-md-down-none" style="width: 44%">
						<div class="card-body text-center">
							<div>
								<h2>宣言·远景·使命</h2>
								<p class="text-left">
									<br>
									企业精神，品质第一。<br>
									品质第一，客户至上。 <br>
									顾客至上，改革求实。 <br>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>