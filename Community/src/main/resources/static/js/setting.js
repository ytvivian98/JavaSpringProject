$(function (){
   $("#uploadForm").submit(upload);
});

function upload(){

    $.ajax({
        url:"http://up.qiniup.com",
        method:"post",
        processData:false,//不要把表单的内容转化为字符串
        contentType:false,//不让JQuery设置上传类型，浏览器会自动设置
        data:new FormData($("#uploadForm")[0]), //$("#uploadForm")为jQuery选择器选中的一块
        success: function (data){
            if(data && data.code == 0){
                //成功了
                $.post(
                  CONTEXT_PATH+"/user/header/url",
                    {"fileName":$("input[name='key']").val()},
                    function (data){
                      data = $.parseJSON(data);
                      if(data.code == 0){
                          window.location.reload();
                      }
                      else
                      {
                          alert(data.msg);
                      }
                    }
                );
            }
            else{
                alert("上传失败！")
            }
        }
    });
    return false;//不用在提交表单了，上面已经处理完逻辑了
}