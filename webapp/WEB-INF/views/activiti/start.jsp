<%--
  Created by IntelliJ IDEA.
  User: song
  Date: 2017/2/28
  Time: 上午10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="/js/jquery-2.2.1.min.js"></script>
    <title>Title</title>
</head>
<body>
手动生成
    <form action="/activity/process/startForm/vocation" method="post">
    <p>请假天数: <input type="text" name="days" /></p>
    <p>理由: <input type="text" name="description" /></p>
    <input type="submit" value="Submit" />
</form>
</body>
<script>
    $.ajax({
        type: "GET",
        url: "/activity/process/startForm/vocation",
        dataType: 'json',
        contentType : 'application/json',
        success: function(data){
            console.log(data);
//            $   append dom form + input
        },
        error: function(data){
            console.log("-------" + data);
            alert("操作失败");
        }
    });
</script>
</html>
