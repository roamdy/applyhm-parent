/**
 * 菜单管理-js
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
var treegrid;
$(function(){
	treegrid = $("#treegrid").treegrid({
		method:'GET',
		url : projectName + '/sys/res/getSyncGridTree',
		idField : 'id',
		treeField : 'resName',
		parentField : 'parentId',
		fit : true,
		fitColumns : true,
		border : false,
		singleSelect:true,
		rownumbers: true,
		toolbar:[{
			text:"新增",
			iconCls : 'icon-add',
			handler : add
		},{
			text:"编辑",
			iconCls : 'icon-edit',
			handler : edit
		},{
			text:"删除",
			iconCls : 'icon-remove',
			handler : del
		},'-',{
			text:"展开",
			iconCls : 'icon-redo',
			handler : function() {
				expandAll();
			}
		},{
			text:"收起",
			iconCls : 'icon-undo',
			handler : function() {
				collapseAll();
			}
		}],
		frozenColumns : [ [ {
			title : 'id',
			field : 'id',
			width : 50,
			hidden : true
		},{
			field : 'resName',
			title : '资源名称',
			width : 200
		}] ],
		columns : [ [ {
			field : 'src',
			title : '资源地址',
			width : 50
		}] ]
		/*onBeforeExpand:function(row,data){
			var t = $(this);
			if (data) {
				$(data).each(function(index, d) {
					if (this.state == 'closed' && this.levelNum == 0) {
						t.treegrid('expandAll');
					}
				});
			}
		}*/
	});
});
	
function collapseAll() {
	var node = treegrid.treegrid('getSelected');
	if (node) {
		$('#treegrid').treegrid('collapseAll', node.id);
	} else {
		$('#treegrid').treegrid('collapseAll');
	}
}

function expandAll() {
	var node = treegrid.treegrid('getSelected');
	if (node) {
		$('#treegrid').treegrid('expandAll', node.id);
	} else {
		$('#treegrid').treegrid('expandAll');
	}
}

function add(obj){
	var node = treegrid.treegrid('getSelected');
	if (node == null){
		$.messager.show({"title":"系统提示","msg":"请选择一条上级记录","timeout":timeout});
		return;
	}
	var levelNum = node.levelNum;
	if(levelNum >= 1){
		$.messager.show({
			"title" : "系统提示",
			"msg" : "不能在当前节点添加子节点",
			"timeout" : timeout
		});
		return;
	}
	var win;
	win = $("<div></div>").dialog({
		title:'新增',
		width:600,
		height:400,
		modal:true,
		href:projectName + '/sys/res/toAdd?parentId='+node.id+"&levelNum="+(levelNum+1),
		onClose:function(){
			$(this).dialog("destroy");
		},
		buttons:[{
			text:'确定',
		    iconCls:'icon-ok',
		    handler:function(){
		    	 $("#addResForm").form('submit',{
		    		 type:'POST',
		    		 url:projectName + '/sys/res/add',
		    		 success:function(responseData){
		    			 if(responseData){
		    				var data = $.parseJSON(responseData);
		    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
		    			 	if(data.success){
		    			 		$.messager.confirm("提示", "继续新增吗？", function(r) {
									if (r) {

									} else {
										$("#treegrid").treegrid("reload");
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
		 		 win.dialog('destroy');
		 	 }   
		  }]
	});
}

function edit(){
	var obj = treegrid.treegrid('getSelected');
	var win;
	var resId = obj.id;
	win = $("<div></div>").dialog({
		title:'编辑',
		width:600,
		height:400,
		modal:true,
		href:projectName + '/sys/res/toEdit?id=' + resId,
		onClose:function(){
	    	$(this).dialog("destroy");
	    },
		buttons:[{
				text:'确定',
			    iconCls:'icon-ok',
			    handler:function(){
			    	 $("#addResForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName + '/sys/res/update',
			    		 success:function(responseData){
			    			 win.dialog('destroy');
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    					$("#treegrid").treegrid("reload");
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

function del(){
	var obj = treegrid.treegrid('getSelected');
	var resId = obj.id;
	$.messager.confirm("提示","确定删除此记录？",function(r){
		if(r){
			$.ajax({
				type:"POST",
				 url:projectName + '/sys/res/deleteById?id=' + resId,
				dataType:"json",
				success:function(data){
					if(data){
	    				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
	    				if(data.success){
	    					$("#treegrid").treegrid("reload");
	    				}
	    			 }
					
				}
			});
		}
	});
}

