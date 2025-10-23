<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    // 当日の日付取得
    LocalDate today = LocalDate.now();
    String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    String randomTheme = (String) request.getAttribute("randomTheme");
    if (randomTheme == null) {
        randomTheme = "今日の天気について";  // デフォルトの仮お題
    }

    String savedTitle = (String) request.getAttribute("savedTitle");
    String savedText = (String) request.getAttribute("savedText");
    Boolean savedIsPublic = (Boolean) request.getAttribute("savedIsPublic");

    String title = savedTitle != null ? savedTitle : randomTheme;
    String text = savedText != null ? savedText : "";
    boolean isPublic = savedIsPublic != null ? savedIsPublic : false;

    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規日記投稿</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="/common/secondHeader.jsp" %>

<h1>Today's Diary Post</h1>

<p>Date：<strong><%= formattedDate %></strong></p>

<% if (errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
<% } %>

<form action="PostServlet" method="post">
    <label for="title">title:</label><br>
    <input type="text" id="title" name="title" value="<%= title %>" required><br><br>

    <label for="text">text:</label><br>
    <textarea id="text" name="text" rows="10" cols="50" required><%= text %></textarea><br><br>

    <label>
        <input type="checkbox" name="isPublic" value="true" <%= isPublic ? "checked" : "" %> >
        public
    </label><br><br>

    <button type="submit" class="button">Post</button>
</form>

<a href="MyDiaryServlet"><button type="button" class="button">Cancel</button></a>

</body>
</html>
