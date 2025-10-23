<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録確認画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/common/firstHeader.jsp" %>
<h1>Is everything OK?</h1>

<%
    String nickname = request.getParameter("nickname");
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");
%>

<form action="NewAduserServlet" method="post">
    <!-- 表示 -->
    <p>ニックネーム: <strong><%= nickname %></strong></p>
    <p>ユーザーID: <strong><%= userId %></strong></p>
    <p>パスワード: <strong>********</strong></p>

    <!-- 隠しフィールド -->
    <input type="hidden" name="nickname" value="<%= nickname %>">
    <input type="hidden" name="userId" value="<%= userId %>">
    <input type="hidden" name="password" value="<%= password %>">

    <!-- OKボタンの名前を action にして値 confirm を渡す -->
    <button type="submit" name="action" value="confirm" class="button">OK</button>
</form>


<a href="NewAduserServlet"><button type="button" class="button">Cancel</button></a>

</body>
</html>
