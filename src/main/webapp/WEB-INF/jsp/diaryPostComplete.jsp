<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿完了画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/common/secondHeader.jsp" %>
<h1>Post Submitted</h1>
<p><%= request.getAttribute("message") %></p>

<a href="MyDiaryServlet"><button type="button" class="button">MyDiary</button></a>

</body>
</html>
