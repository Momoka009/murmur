<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/common/firstHeader.jsp" %>
<h1>Logout</h1>
<p>Thanks for using Murmur!</p>
<p>See you again!</p>

<a href="LoginServlet"><button type="button" class="button" >Login</button></a>
</body>
</html>