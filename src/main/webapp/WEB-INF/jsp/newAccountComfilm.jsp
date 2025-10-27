<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    model.UsersBean user = (model.UsersBean) request.getAttribute("user");
%>

<form action="NewAduserServlet" method="post">
    <!-- 表示 -->
    <p>ニックネーム: <strong><%= user.getNickname() %></strong></p>
    <p>ユーザーID: <strong><%= user.getUserId() %></strong></p>
    <p>パスワード: <strong>********</strong></p>

    <!-- 隠しフィールド -->
    <input type="hidden" name="nickname" value="<%= user.getNickname() %>">
    <input type="hidden" name="userId" value="<%= user.getUserId() %>">
    <input type="hidden" name="password" value="<%= user.getPassword() %>">

    <button type="submit" name="action" value="confirm" class="button">OK</button>
</form>

<form action="NewAduserServlet" method="get">
    <button type="submit" class="button">Cancel</button>
</form>

</body>
</html>
