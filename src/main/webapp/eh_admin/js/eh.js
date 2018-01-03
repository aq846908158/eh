//全局变量定义（必须引用此文件）---------------------------------------------------------------------------------
var serverURL="http://localhost:8080";
// var serverURL="http://www.jgdmhlg.xin/eh";
//end全局变量定义（必须引用此文件）---------------------------------------------------------------------------------

//获取网页头部 Admin登录信息
function getheaderMessage() {
    $.ajax({
        type: "GET",
        url: serverURL+"/admin/getLoginMessage",
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
                $("#loginNumber").html(data.item.loginNum);
                $('#fast-loginTime').html(getLocalTime(data.item.lastTime));
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
/**
 * 时间戳转时间
 */
function getLocalTime(nS) {
    ns= new Date(parseInt(nS));
    return formatDate(ns);
}
/**
 * 格式化Date类型
 */
function formatDate(now) {
    var year=now.getYear();
    var month=now.getMonth()+1;
    var date=now.getDate();
    var hour=now.getHours();
    var minute=now.getMinutes();
    var second=now.getSeconds();
    return "20"+(year-100)+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
}

/**
 * 下一页
 */
function   pageNext()   {
    pageIndex>pageCount?pageIndex=pageCount:pageIndex++;
    select();
}

/**
 * 上一页
 */
function   pageBack()   {
    pageIndex<=1?pageIndex=1:pageIndex--;
    select();
}

/**
 * 排序类型切换
 */
function   sortType()   {
    var val=$('input:radio[name="sortType"]:checked').val();
    if(val=="desc")
    {
        $("#sortIoc").removeClass("triangle-down");
        $("#sortIoc").addClass("triangle-up");
        $( "#asc" )[ 0 ].checked = true;
        select();
    }
    else {
        $("#sortIoc").removeClass("triangle-up");
        $("#sortIoc").addClass("triangle-down");
        $( "#desc" )[ 0 ].checked = true;
        select();
    }
}

/**
 * 排序字段切换
 */
$(".sortName").click(function () {
    $(".sortName").removeClass("pageNum");
   $(this).addClass("pageNum");
    $(this).next()[0].checked=true;
    select();
});



/*
*
* */
$('#info').click(function () {
    layer_show("个人信息","admin-info.html","800","700");
});