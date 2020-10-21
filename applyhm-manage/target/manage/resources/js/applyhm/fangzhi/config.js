//修改
function edit(id){
	$("#fangzhiConfigForm").form('submit',{
		 type:'POST',
		 url : projectName+'/fangzhi/config/admin/update',
		 success:function(responseData){
			 if(responseData){
				var data = $.parseJSON(responseData);
				$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
				if(data.success){

				}
			 }
		 }
	});
}

//重置
function reset(){
	$("#fangzhiConfigForm").form("reset");
}
	