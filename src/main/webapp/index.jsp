<%--
  Created by IntelliJ IDEA.
  User: 丹青
  Date: 2019/10/2
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>文件上传</h1>
    <form action="user/fileupload1" method="post" enctype="multipart/form-data">
        选择文件：<input type="file" name="upload"/><br/>
        <input type="submit" value="上传">
    </form>
    <h1>springmvc文件上传</h1>
    <form action="user/fileupload2" method="post" enctype="multipart/form-data">
        选择文件：<input type="file" name="upload"/><br/>
        <input type="submit" value="上传">
    </form>
    ------------------------------------------------------------
    <a href="download?filename=1.jpg">文件下载</a>
</body>
</html>
