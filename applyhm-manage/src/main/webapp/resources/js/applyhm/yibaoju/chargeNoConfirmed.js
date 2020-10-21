/**
 * 初始化配置
 */
var datagrid;

$(function() {
	datagrid = $('#datagrid').datagrid({
		method:'get',
		url : projectName+'/yibaoju/charge/admin/list?status_s=0&v_date=' + new Date().getTime(),
		pagination : true,
		pageSize : 200,
		pageList : [ 50,100,200 ],
		fit : true,
		fitColumns : false,
		nowrap : false,
		border : false,
		idField : 'id',
		singleSelect:true,
		rownumbers: true,
		nowrap:false,
		toolbar:[{
			text:"导出",
			iconCls : 'icon-add',
			handler : function exp(){
				exportExcel();
			}
		},{
			text:"查看",
			iconCls : 'icon-search',
			handler : query
		},{
			text:"刷新",
			iconCls : 'icon-reload',
			handler : reload
		}],
		frozenColumns : [[/*{
			field : 'opt',
			title : '操作选项',
			align : 'center',
			width : 100,
			formatter:function(value,row,index){
				var handleHtml = '';
				handleHtml += '<a href="javascript:query(\'' + row.id + '\')">查看更多</a>&nbsp;';
				handleHtml += '<a href="javascript:edit(\'' + row.id + '\')">修改</a>&nbsp;';
				return handleHtml;
			}
		}, */{
			title : '发票号',
			field : 'chargeNo',
			align : 'center',
			width : 120,
			sortable:true
		}, {
			title : '姓名',
			field : 'userName',
			align : 'center',
			width : 100,
			sortable:true
		},{
			title : '性别',
			field : 'sex',
			align : 'center',
			width : 70,
			sortable:true,
			formatter:function(value,row,index){
				if(value){
					if(value == '2' ) {
						return "女";
					} else if( value == '1' ) {
						return "男";
					}
				}
			}
		}, {
			title : '费用总额',
			field : 'chargeNum',
			align : 'center',
			width : 120,
			sortable:true
		}, {
			title : '创建时间',
			field : 'createTime',
			align : 'center',
			width : 170,
			sortable:true,
			formatter:function(value,row,index){
				if(value){
					return value.substring(0,19);
				}
				return value;
			}
		}, {
			title : '住院医院',
			field : 'hospitalName',
			align : 'left',
			width : 160,
			sortable:true
		}, {
			title : '操作单位',
			field : 'yibaojuName',
			align : 'left',
			width : 160,
			sortable:true
		}, {
			title : '状态',
			field : 'status',
			align : 'center',
			width : 80,
			sortable:true,
			formatter:function(value,row,index){
				if(value === 0) {
				   return "<font color=blue>审核中</font>";
				} else if(value === 1){
				   return "<font color=black>已通过</font>";
				} else if(value === 2){
				   return "<font color=red>未通过</font>";
				}
			}
		}, {
			title : '报告',
			field : 'baogao_url',
			align : 'center',
			width : 80,
			sortable:true,
			formatter:function(value,row,index){
				if(value.length != 0 && readBaogao.length != 0 && 'N' === readBaogao) {
					return "无权查看";
				}
				if(value.length != 0) {
					var result = "'" + value + "'";
					return "<a href='javascript:void(0);' onclick=\"queryBaogao("+row.id+","+result+")\">查看报告</a>";
				}
			}
		}, {
			title : '打印状态',
			field : 'print_status',
			align : 'center',
			width : 150,
			sortable:true,
			formatter:function(value,row,index){
				if(value === 0) {
				   return "<font color=blue>未打印</font>";
				} else if(value === 1){
				   return "<font color=red>已打印</font>";
				}
			}
		}
		]],
		//双击事件
		onDblClickRow: function (index, row) {
			edit(row.id)
		}
	});

});

//查看
function query(){
	var rows = datagrid.datagrid('getSelections');
	if(rows.length != 1) {
		$.messager.show({"title":"系统提示","msg":"请选择后单记录操作","timeout":1000});
		return;
	}
	var id = rows[0].id;
	win = $("<div></div>").dialog({
		title:'查看',
		width:830,
		height:'90%',
		maximizable:true,
		modal:true,
		href:projectName+'/yibaoju/charge/admin/toEdit?id='+id,
		onClose:function(){
				$(this).dialog("destroy");
		},
		buttons:[{
				 text:'取消',
				 iconCls:'icon-cancel',
				 handler:function(){
					 win.dialog('destroy');
				 }
			  }]
	});
}

//修改
function edit(){
	var rows = datagrid.datagrid('getSelections');
	if(rows.length != 1) {
		$.messager.show({"title":"系统提示","msg":"请选择后单记录操作","timeout":1000});
		return;
	}
	var id = rows[0].id;
	win = $("<div></div>").dialog({
		title:'修改',
		width:850,
		height:'90%',
		maximizable:true,
		modal:true,
		href:projectName+'/yibaoju/charge/admin/toEdit?id='+id,
		onClose:function(){
				$(this).dialog("destroy");
		},
		buttons:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
						$("#yibaojuChargeForm").form('submit',{
							 type:'POST',
							 url : projectName+'/yibaoju/charge/admin/update',
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

//刷新
function reload(){
	$("#datagrid").datagrid("reload");
}

//搜索
function doSearch(){
	$("#datagrid").datagrid("load", serializeObject($("#yibaojuChargeSearchForm")));
}

//重置
function reset(){
	$("#yibaojuChargeSearchForm").form("reset");
}

//导出
function exportExcel(){
	window.location.href = projectName + '/yibaoju/charge/admin/exportExcel?status_s=0&' + $("#yibaojuChargeSearchForm").serialize();
}

//打印
function doPrint(id){
	$.messager.confirm("提示","确定打印？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				url:projectName+'/yibaoju/charge/admin/doPrint?id=' + id,
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