<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.UsersBean" %>
<%
    UsersBean user = (UsersBean) session.getAttribute("loginUser");
    if (user == null) {
        response.sendRedirect("LoginServlet");
        return;
    }
    String userId = user.getUserId();
    String nickname = user.getNickname();
    int currentStreak = user.getCurrentStreak();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>アカウント情報</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="/common/secondHeader.jsp" %>

<h1>My Account</h1>

<div class="account-info">
    <p><strong>User ID:</strong><%= userId %></p>
    <p><strong>Nickname:</strong> <%= nickname %></p>
    <p><strong>Post Streak:</strong> <%= currentStreak %> days!!</p>
</div>

<br>

<nav>
  <ul>
    <li><a href="PostServlet">Today's Diary Post</a></li>
    <li><a href="MyDiaryServlet">My Diary</a></li>
    <li><a href="SomeonesDiaryViewServlet">Someone's Diary</a></li>
    <li><a href="LogoutServlet">Logout</a></li>
  </ul>
</nav>

</body>
</html>
