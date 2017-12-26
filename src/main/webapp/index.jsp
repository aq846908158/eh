<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<body>
<h2>Hello World!</h2>

<form action="<%=basePath%>user/login/" method="post">
    <input name="name">

    <input type="submit" value="提交">

</form>
</body>
</html>
