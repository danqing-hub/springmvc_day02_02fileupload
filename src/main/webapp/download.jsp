<%@ page import="java.net.URLEncoder" %><%--
  Created by IntelliJ IDEA.
  User: 丹青
  Date: 2019/10/3
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="${pageContext.request.contextPath }/download?filename=<%=URLEncoder.encode("壁纸.jpg", "UTF-8")%>">
    中文名称文件下载${pageContext.request.contextPath }
</a>
</body>
</html>
