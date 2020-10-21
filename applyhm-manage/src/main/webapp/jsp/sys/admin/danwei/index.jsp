<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div region="north"  id="roleArea" style="padding: 5px; height: 50px;">
		<div style="margin-bottom: 5px">
			<form id="danweiSearchForm"  method="post">
				<table class="table_content" border="0" >
					<tr>
						<td class="tar" >类型：</td>
						<td class="tal" >
							<input id="type" name="type" 
							data-options="required:false,editable:false, url:'<%=request.getContextPath()%>/resources/data/danwei_type.txt', valueField:'id', textField:'text', method:'GET',
							onSelect:function(record){
								
							}
							" class="easyui-combobox" style="width: 200px;" prompt="---------请选择---------"/>
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
	<div data-options="region:'center'"  border="false" style="overflow: hidden;width:85%">
    	<table id="datagrid"></table>
    </div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/applyhm/sys/danwei.js?date=<%=System.currentTimeMillis() %>"></script>
</html>