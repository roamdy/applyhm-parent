<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div region="north"  id="helpArea" style="white-space: nowrap;padding: 5px;">
		<div style="margin-top: 2px;">
			<form id="yibaojuChargeSearchForm" method="post">
				<table class="table_content"  border="0" >
					<tr>
						<td class="tar" >发票号：</td>
						<td class="tal" >
							<input name="chargeNo_s" class="easyui-textbox" data-options="required:false"
								style="width: 150px;" />
						</td>
						<td class="tar" >患者姓名：</td>
						<td class="tal" >
							<input name="userName_s" class="easyui-textbox" data-options="required:false"
								style="width: 150px;" />
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
						<td class="tar">状态</td>
						<td class="tal">
							<input name="status_s" data-options="required:false,readonly:false,url:'<%=request.getContextPath()%>/resources/data/status.json', valueField:'id', textField:'text', panelHeight:'auto'" 
								class="easyui-combobox" style="width: 150px;" prompt="--请选择--"/>
						</td>
					</tr>
					<tr>
						<td class="tar" >医保局：</td>
						<td class="tal" >
							<input name="ownerDanwei_s" data-options="mode: 'remote', url:'<%=request.getContextPath()%>/danwei/danwei/getList?type=yibaoju', valueField:'id', textField:'danwei' " 
								class="easyui-combobox" style="width: 150px;" prompt="--请选择--"/>
						</td>
						<td class="tar" >医院：</td>
						<td class="tal" >
							<input name="hospital_s" data-options="mode: 'remote', url:'<%=request.getContextPath()%>/danwei/danwei/getList?type=hospital', valueField:'id', textField:'danwei' " 
								class="easyui-combobox" style="width: 150px;" prompt="--请选择--"/>
						</td>
						<td class="tar" colspan="6">
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
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/applyhm/yibaoju/chargeManager.js?date=<%=System.currentTimeMillis() %>"></script>
</html>