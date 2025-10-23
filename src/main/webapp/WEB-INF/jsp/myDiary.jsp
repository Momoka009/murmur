<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.PostBean" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>日記一覧</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/common/secondHeader.jsp" %>
    <h1>My Diary</h1>

    <form action="PostServlet" method="get">
        <button type="submit" class="button">New Post ✎</button>
    </form>
<%
    List<PostBean> posts = (List<PostBean>) request.getAttribute("posts");
    if (posts == null || posts.isEmpty()) {
%>
    <p>No post</p>
<%
    } else {
%>
    <div class="diary-list">
<%
        for (PostBean post : posts) {
%>
        <div class="diary-card">
            <h3><%= post.getTitle() %></h3>
            <p><%= post.getText() %></p>
	    <div class="post-meta">
	    <p class="public-status"><%= post.Is_public() ? "🔓public" : "🔐private" %></p>
	    <p class="date"><%= post.getCreated_at() %></p>
	    <p class="likes">❤️ <%= post.getLikeCount() %></p>
		</div>

            <form action="EditDiaryServlet" method="get" class="edit-form">
                <input type="hidden" name="postId" value="<%= post.getPost_id() %>">
                <button type="submit" class="edit-button">Edit</button>
            </form>
        </div>
<%
        }
%>
    </div> 
<%
    }
%>


</body>
</html>
