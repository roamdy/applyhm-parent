<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div region="north"  id="helpArea" style="white-space: nowrap;padding: 5px; height: 50px;">
		<div style="margin-top: 2px;">
			<form id="yibaojuChargeSearchForm" method="post">
				<table class="table_content"  border="0" >
					<tr>
						<td class="tar" >发票号：</td>
						<td class="tal" >
							<input name="chargeNo_s" class="easyui-textbox" data-options="required:false"
								style="width: 120px;" />
						</td>
						<td class="tar" >患者姓名：</td>
						<td class="tal" >
							<input name="userName_s" class="easyui-textbox" data-options="required:false"
								style="width: 120px;" />
						</td>
						<td class="tar" >开始时间：</td>
						<td class="tal" >
							<input name="beginTime_s" class="easyui-datebox" data-options="readonly:false,required:false"
								style="width: 150px;" />
						</td>
						<td class="tar" >结束时间：</td>
						<td class="tal" >
							<input name="endTime_s" class="easyui-datebox" data-options="readonly:false,required:false"
								style="width: 150px;" />
						</td>
					    <td style="padding-left:20px">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch()">搜索</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px" onclick="reset()">重置</a>
						</td> 
					</tr>
				</table>
			</form>
		</div>
	</div> 
	<div data-options="region:'center'" border="false" style="overflow: hidden;width:85%">
    	<table id="datagrid"></table>
    </div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/applyhm/yibaoju/chargeNoConfirmed.js?date=<%=System.currentTimeMillis() %>"></script>
</html>