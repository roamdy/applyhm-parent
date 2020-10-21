<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<title>管理平台</title>
<link rel="Shortcut Icon" href="resources/images/favicon.ico">
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
<c:if test="${ sessionScope.sessionInfo == null}">
	<c:redirect url="login.jsp"/>
</c:if>
</head>
	<body id="indexLayout" class="easyui-layout" fit="true">
		<c:if test="${sessionInfo.user.roleId == 5 }">
			<div region="north" href="<%=request.getContextPath()%>/north" style="height:90px;width:100%;overflow:hidden;background: url('<%=request.getContextPath()%>/resources/images/bg/fangzhi.png')"></div>
		</c:if>
		<c:if test="${sessionInfo.user.roleId != 5 }">
			<div region="north" href="<%=request.getContextPath()%>/north" style="height:90px;width:100%;overflow:hidden;background: url('<%=request.getContextPath()%>/resources/images/bg/top.png?v=2019')"></div>
		</c:if>
		<div region="west" href="<%=request.getContextPath()%>/west" title="菜单" split="true" iconCls="icon-man" style="width: 200px; overflow: hidden;"></div>
		<div region="center" href="<%=request.getContextPath()%>/center" title="" style="overflow: hidden;"></div>
		<c:if test="${sessionInfo.user.roleId != 5 }">
			<div region="south" href="<%=request.getContextPath()%>/south" style="height: 30px; overflow: hidden;"></div>
		</c:if>
	</body>
</html>
<script type="text/javascript">
    var stompClient = null;

    function connect() {
        var userid = '${sessionInfo.user.userName}';
        var socket = new SockJS(projectName+"/hello");
        stompClient = Stomp.over(socket);
        stompClient.connect({
       		
        }, function(frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function(message){
                showGreeting(message.body);
            });
            stompClient.subscribe('/user/' + userid + '/message',function(message){
                showGreeting(message.body);
            });
        });
    }

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
            stompClient = null;
        }
    }

    function showGreeting(message) {
    	$.messager.show({"title":"系统提示","msg":""+message+"","timeout":3600000});
    }
</script>