$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	//获取标题和内容
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	//发送异步请求
	$.post(
		//访问路径
		CONTEXT_PATH + "/discuss/add",
		//传入的数据
		{
			"title":title,
			"content":content
		},
		//回调函数，处理返回结果
		function (data){
			data = $.parseJSON(data);
			//在提示框中显示提示的消息
			$("#hintBody").text(data.msg);
			//显示提示框
			$("#hintModal").modal("show");
			//过两秒自动隐藏
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新页面
				if(data.code == 0){
					window.location.reload();
				}
			}, 2000);
		}
	);


}