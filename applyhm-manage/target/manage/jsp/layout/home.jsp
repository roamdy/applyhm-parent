<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../resources/inc/meta.jsp"></jsp:include>
<jsp:include page="../../resources/inc/easyui.jsp"></jsp:include>
<jsp:include page="../../resources/inc/easyui-portal.jsp"></jsp:include>
<script type="text/javascript">
	var portal;
	var col;
	$(function() {
		col = $('#portal div').length;
		portal = $('#portal').portal({
			border : false,
			fit : true
		});
	});
</script>
</head>
<body class="easyui-layout" fit="true" style="/* background-color:#CDE4E7; */">
	<div region="center" style="overflow: hidden;text-align:center;background-attachment:fixed;" border="false">
		<div id="portal" style="position:relative;">
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<c:if test="${sessionInfo.user.roleId != 5 }">
				<h3>医疗信息核实服务平台</h3>
			</c:if>
			<c:if test="${sessionInfo.user.roleId == 5 }">
				<h3>管理信息平台</h3>
			</c:if>
		</div>
	</div>
</body>
</html>