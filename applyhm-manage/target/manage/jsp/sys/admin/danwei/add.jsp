<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div align="center">
	<form id="danweiForm" method="post">
		<table class="tableForm">
			<tr>
				<th width="10%">类型：</th>
				<td>
					<input id="type" name="type" value="yibaoju" type="hidden"/>
					医保局
				</td>
			</tr>
			<tr>
				<th>名称：</th>
				<td>
					<input name="danwei" value="${danweiVo.danwei}" data-options="required:true" class="easyui-textbox" style="width: 200px;"/>
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td>
					<input name="remark" value="${danweiVo.remark}" data-options="required:false" class="easyui-textbox" style="width: 200px;" />
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${danweiVo.id}"/>
	</form>
</div>