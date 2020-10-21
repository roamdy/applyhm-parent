/**
 * 管理
 */
var datagrid;
$(function() {
	datagrid = $('#datagrid').datagrid({
		method:'get',
		url : projectName+'/danwei/danwei/admin/list?v_date=' + new Date(),
		pagination : true,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : false,
		idField : 'id',
		singleSelect:true,
		rownumbers: true,
		toolbar:[ {
			text:"新增",
			iconCls : 'icon-add',
			handler : add
		},{
			text:"刷新",
			iconCls : 'icon-reload',
			handler : reload
		}],
		frozenColumns : [[{
			field : 'id',
			title:'序号',
			align:'center',
			hidden:true,
			width : 50
		}, {
			title : '类型',
			field : 'typeName',
			align:'center',
			width : 200,
			formatter:function(value,row,index){
				var handleHtml = "";
				if(row.type == 'hospital') {
					handleHtml += "医院";
				}
				if(row.type == 'yibaoju') {
					handleHtml += "医保局";
				}
				return handleHtml;
			}
		}, {
			title : '名称',
			field : 'danwei',
			align:'center',
			width : 200
		}, {
			title : '备注',
			field : 'remark',
			align:'center',
			width : 200
		}, {field : 'opt',
			title:'操作选项',
			align:'center',
			width : 300,
			formatter:function(value,row,index){
				if(row.type == 'hospital') {
					return "";
				}
				var handleHtml = "";
				handleHtml += "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>&nbsp;";
				return handleHtml;
			}
		}] ]
	});

});

//新增
function add(obj){
	var win;
	win = $("<div></div>").dialog({
		title:'新增',
		width:'600px',
		height:'400px',
		modal:true,
		href:projectName+'/danwei/danwei/admin/toAdd',
		onClose:function(){
			$(this).dialog("destroy");
		},
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				$("#danweiForm").form('submit',{
					 type:'POST',
					 url:projectName+'/danwei/danwei/admin/add',
					 success:function(responseData){
						 if(responseData){
							var data = $.parseJSON(responseData);
							$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
							if(data.success){
								$.messager.confirm("提示", "继续新增吗？", function(r) {
									if (r) {

									}else{
										$("#datagrid").datagrid("reload");
										win.dialog('destroy');
									}
								})
							}
						 }
					 }
				 });
			 }
		   },{
			 text:'取消',
			 iconCls:'icon-cancel',
			 handler:function(){
				 $("#datagrid").datagrid("reload");
				 win.dialog('destroy');
			 }
		  }]
	});
}

//修改
function edit(sid){
	win = $("<div></div>").dialog({
		title:'编辑',
		width:'600px',
		height:'400px',
		modal:true,
		href:projectName+'/danwei/danwei/admin/toEdit?id=' + sid,
		onClose:function(){
			$(this).dialog("destroy");
		},
		buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					$("#danweiForm").form('submit',{
						 type:'POST',
						 url:projectName+'/danwei/danwei/admin/update',
						 success:function(responseData){
							  win.dialog('destroy');
							 if(responseData){
								var data = $.parseJSON(responseData);
								$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
								if(data.success){
									$("#datagrid").datagrid("reload");
								}
							 }
						 }
					 });
				 }
			   },{
				 text:'取消',
				 iconCls:'icon-cancel',
				 handler:function(){
					 win.dialog('destroy');
				 }
			  }]
	});
}

//删除
function del(sid){
	$.messager.confirm("提示","确定删除此记录吗？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				url:projectName+'/danwei/danwei/admin/deleteById?id=' + sid,
				dataType:"json",
				success:function(data){
					if(data){
						$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
						if(data.success){
							$("#datagrid").datagrid("reload");
						}
					 }
				}
			});
		}
	});
}

//刷新
function reload(){
	$("#datagrid").datagrid("reload");
}

//搜索
function doSearch(){
	$("#datagrid").datagrid("load",serializeObject($("#danweiSearchForm")));
}

//重置
function reset(){
	$("#danweiSearchForm").form("reset");
}
