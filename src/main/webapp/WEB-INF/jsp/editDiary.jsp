<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="model.PostBean" %>
<%
    // 編集対象の日記（PostBean）をリクエストスコープから取得（サーブレットから渡される想定）
    PostBean post = (PostBean) request.getAttribute("post");

    if (post == null) {
%>
    <p>対象の日記が見つかりません。</p>
<%
    } else {
        String title = post.getTitle();
        String text = post.getText();
        String createdAt = post.getCreated_at().toString(); // 日付（例: 2025-10-16）
        String nickname = post.getUser().getNickname();
        boolean isPublic = post.Is_public();

        String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>日記編集画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/common/secondHeader.jsp" %>
<h1>Edit Diary</h1>

<p>created at：<%= createdAt %></p>

<% if (errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
<% } %>

<form action="EditDiaryServlet" method="post">
    <input type="hidden" name="postId" value="<%= post.getPost_id() %>">

    <label for="title">title：</label><br>
    <input type="text" id="title" name="title" value="<%= title %>" required><br><br>

    <label for="text">text：</label><br>
    <textarea id="text" name="text" rows="10" cols="50" required><%= text %></textarea><br><br>

    <label>
        <input type="checkbox" name="isPublic" value="true" <%= isPublic ? "checked" : "" %> >
        public
    </label><br><br>

    <button type="submit" class="button">Edit</button>
</form>

<a href="MyDiaryServlet"><button type="button" class="button">Cancel</button></a>

</body>
</html>
<%
    }
%>
