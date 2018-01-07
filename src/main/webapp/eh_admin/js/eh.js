//全局变量定义（必须引用此文件）---------------------------------------------------------------------------------
var serverURL="http://localhost:8080";
// var serverURL="http://www.jgdmhlg.xin/eh";
//end全局变量定义（必须引用此文件）---------------------------------------------------------------------------------
var  eh_key=false;
var pageSize=3;
//获取网页头部 Admin登录信息
function getheaderMessage() {
    $.ajax({
        type: "GET",
        url: serverURL+"/admin/getLoginMessage",
        async:false,//同步请求
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

                var msg='';
                if (item.phone == null || item.email == null){
                    if(item.phone == null){
                        msg='联系方式';
                    }
                    if (item.email == null){
                        msg+='、电子邮箱';
                    }
                    var infoTitle='您的'+msg+'为空，请添加完善，如不完善，则无法进行相关操作!!';
                    test(infoTitle);//弹出警告层
                    eh_key=true;
                }

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
 * 让提交按钮失效
 */
$('#mit').click(function () {

    select();
    return false;
})


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


function  test(txt) {
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句

        var active = {
            notice: function(){
                //示范一个公告层
                layer.open({
                    type: 1
                    ,title: false //不显示标题栏
                    ,closeBtn: false
                    ,area: '300px;' //宽度
                    ,shade: 1 //透明度
                    ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    ,btn: ['去完善','忽略']
                    ,btnAlign: 'c'
                    ,moveType: 1 //拖拽模式，0或者1
                    ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">'+txt+'</div>'
                    ,success: function(layero){
                        var btn = layero.find('.layui-layer-btn');
                        btn.find('.layui-layer-btn0').attr({
                            href: 'admin-info.html'
                            ,target: '_top'
                        });
                    }
                });
            }
        }

        //$('#layerDemo .layui-btn').click( function(){
        //var othis = $(this), method = 'notice';
        active['notice'] ? active[ 'notice'].call(this, $(this)) : '';
        //	});


    });

}

/**
 * 分页页数显示控制
 */
function pageFenShow()
{
    //控制分页标签显示--------------------------------------------
    $("#pageNumber").html("");
    var pageCha=pageCount-pageIndex+1;//之后的页数
    var temp=(5-pageCha);//存放还差多少页足够5页
    if(pageCount>5)//超过5页
    {

        if(pageCha>=5) {//如果总页数减去当前显示页还足够5页
            for(var i=pageIndex;i<pageIndex+5;i++){
                if(pageIndex==i){
                    $("#pageNumber").append("<div  class=\"btn pageNum pageTo\"  style=\"width: 30px; padding: 0px; padding-top: 5px;\" onclick='pageTo("+i+")'  >"+i+"</div>");
                }
                else {
                    $("#pageNumber").append("<div  class=\"btn pageTo\"  style=\"width: 30px; padding: 0px; padding-top: 5px;\"  onclick='pageTo("+i+")' >"+i+"</div>");
                }
            }
            $('#pageNumber').append("...");
        }
        else {//总页数减去当前显示页不足5页
            $("#pageNumber").html("");
            for(var i=pageIndex-temp;i<=pageCount	;i++){
                if(pageIndex==i){
                    $("#pageNumber").append("<div  class=\"btn pageNum pageTo\"  style=\"width: 30px; padding: 0px; padding-top: 5px;\"  onclick='pageTo("+i+")' >"+i+"</div>");
                }
                else {
                    $("#pageNumber").append("<div  class=\"btn pageTo\"  style=\"width: 30px; padding: 0px; padding-top: 5px;\"  onclick='pageTo("+i+")' >"+i+"</div>");
                }
            }
        }

    }else{//未超过5页
        for(var i=1;i<=pageCount;i++){
            if(pageIndex==i){
                $("#pageNumber").append("<div  class=\"btn pageNum pageTo\"  style=\"width: 30px; padding: 0px; padding-top: 5px;\"  onclick='pageTo("+i+")' >"+i+"</div>");
            }
            else {
                $("#pageNumber").append("<div  class=\"btn pageTo\"  style=\"width: 30px; padding: 0px; padding-top: 5px;\"  onclick='pageTo("+i+")' >"+i+"</div>");
            }
        }
    }
    //end控制分页标签显示--------------------------------------------
}

/**
 * 点击页码跳转到指定页数
 * @param pageNum 页数
 */
function pageTo(pageNum)
{
    pageIndex=pageNum;
    select();
}