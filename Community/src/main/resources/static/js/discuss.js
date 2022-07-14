function like(btn,entityType,entityId,entityUserId){
    $.post(
      CONTEXT_PATH+"/like",
        {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId},
        function (data){
          data = $.parseJSON(data);
          if(data.code == 0){
              //成功
              $(btn).children("i").text(data.likeCount);
              $(btn).children("b").text(data.likeStatus==1?'已赞':'赞');
          }
          else
          {
              alert(date.msg);
          }
        }
    );
}



function profile(userId){
    window.location.href=CONTEXT_PATH+"/user/profile/"+userId;
}