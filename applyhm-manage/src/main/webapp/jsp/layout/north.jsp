<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<style>
<!--
.memu_1{
	text-decoration:none;
	color:#ffffff;
}
.memu_1:hover{
	color:#000000;
}
-->
</style>
<div style="position: absolute; right: 0px; bottom: 5px;right:15px; font-weight:bolder;font-family:微软雅黑;font-size:13px; ">
	<font color="#032453"><c:if test="${sessionInfo != null}"><strong>${sessionInfo.user.userName}</strong></c:if></font>
	<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-redo'" onclick="modifyPwd();">修改密码</a>
	<c:if test="${sessionInfo.user.roleId != 5 }">
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'" onclick="editUserInfo();">我的信息</a>
	</c:if>
	<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" onclick="logout();">安全退出</a>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div class="menu-sep"></div>
	<div onclick="logout();">注销系统</div>
</div>
<input type="hidden" id="isLiveMode" value="${isLiveMode }"/>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/applyhm/sys/user.js?date=2016050803"></script>
