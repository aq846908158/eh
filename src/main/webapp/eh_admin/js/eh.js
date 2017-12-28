//获取网页头部 Admin登录信息
function getheaderMessage() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/admin/getLoginMessage",
        data:{"token":localStorage.getItem("eh_token")},
        dataType:"json",
        success: function(data){
            if(data.errorCode=="500") {
                layer.msg("请登录");
                window.location.href="login.html";
            }
            if(data.errorCode=="200") {
                var item=data.item;
                $("#adminName").html(data.item.adminName);
                $("#adminTitle").html(data.item.title);

            }
            if (data.errorCode == "501"){
                layer.msg("服务器炸了！！！");
                window.location.href="login.html";
            }
        },
        error:function () {
            layer.msg("服务器繁忙,请稍后再试");
        }
    });
}

//管理员退出
function adminExit() {
    localStorage.removeItem("eh_token");
    window.location.href="login.html";
}