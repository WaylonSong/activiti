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
    <title>task列表</title>
</head>
<body>
task列表
<div class="list">reacting</div><br>
访问地址
http://127.0.0.1:8082/activity/task/execute/{id}
</body>



<script>
    $.ajax({
        type: "GET",
        url: "/runtime/tasks",
        dataType: 'json',
        contentType : 'application/json',
        success: function(result){
            $(".list").html(JSON.stringify(result));
            console.log(result);
        },
        error: function(result){
            console.log("-------" + result);
            alert("操作失败");
        }
    });
</script>
</html>
