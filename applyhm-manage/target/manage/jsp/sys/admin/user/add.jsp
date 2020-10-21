<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div align="center">
	<form id="userForm" method="post">
		<table class="tableForm">
			<tr>
				<th>用户名：</th>
				<td><input name="userName" value="${orgUserVo.userName}" class="easyui-textbox" data-options="required:true"	style="width: 200px;" />
				</td>
				<th>姓名：</th>
				<td><input name="realName" value="${orgUserVo.realName}" class="easyui-textbox" data-options="required:true"	style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>性别：</th>
				<td>
					<select name="sex" class="easyui-combobox" style="width: 200px;" editable=false>
						<option value="true" <c:if test="${orgUserVo.sex==true}" >selected ="selected"</c:if>>男</option>
						<option value="false" <c:if test="${orgUserVo.sex==false}" >selected ="selected"</c:if>>女</option>
					</select>
				</td>
				<th>出生日期：</th>
				<td>
					<input name="birthdayStr" value="${orgUserVo.birthdayStr}" class="easyui-datebox" 	style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>固定电话：</th>
				<td><input name="fixedPhone" value="${orgUserVo.fixedPhone}" class="easyui-textbox" data-options="required:true"	style="width: 200px;" />
				</td>
				<th>移动电话：</th>
				<td><input name="mobilePhone" value="${orgUserVo.mobilePhone}" class="easyui-textbox" data-options="required:true"	style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>电子邮箱：</th>
				<td><input name="email" value="${orgUserVo.email}" class="easyui-textbox" 	style="width: 200px;" />
				</td>
				<th></th>
				<td>
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${orgUserVo.id}" />
		<input name="type" type="hidden" value="${orgUserVo.type}" />
		<input name="userType" type="hidden" value="${type}" />
	</form>
	<script type="text/javascript">
	function change(obj){
		if($(obj).prop("checked")==true){
			$(obj).next("span").children(":checkbox").prop("checked",true);
		}else{
			$(obj).next("span").children(":checkbox").prop("checked",false);
		}
	}
	</script>
</div>