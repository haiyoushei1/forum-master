function post(){
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();

    if(!content){
        alert(不能为空内容);
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success:function (response) {
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code = 1003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open(href="https://github.com/login/oauth/authorize?client_id=95ee29c50a7aab686664&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    } else{
                        alert(response.message);
                    }
                }
            }
            console.log(response);
    },
    dataType:"json"
    });
}