<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.List,java.util.Map,model.PostBean" %>
<%
    // 他ユーザーの日記（最新3件）がサーブレットから渡されているとする
    List<PostBean> posts = (List<PostBean>) request.getAttribute("posts");

    // ログインユーザーのID（自分がどの投稿にいいねしたか判定するため）
    String loginUserId = (String) session.getAttribute("loginUserId");

    // 各ポストに対してログインユーザーが「いいね済み」かのフラグ（Map<postId, true/false>）
    Map<Integer, Boolean> likedMap = (Map<Integer, Boolean>) request.getAttribute("likedMap");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>他ユーザーの日記閲覧画面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/common/secondHeader.jsp" %>
<h1>Someone's Diary</h1>

<% if (posts == null || posts.isEmpty()) { %>
    <p>No post</p>
<% } else { %>
<div class="diary-list">
<% for (PostBean post : posts) {
    int postId = post.getPost_id(); 
    boolean liked = likedMap != null && likedMap.getOrDefault(postId, false);
%>
    <div class="diary-card other-user">
        <h3><%= post.getTitle() %></h3>
        <p><%= post.getText() %></p>
        <p class="public-status">nickname：<%= post.getUser()!=null ? post.getUser().getNickname() : "Unknown" %></p>
        <div class="post-meta">
            <p class="date"><%= post.getCreated_at() %></p>
            <form action="LikesServlet" method="post">
                <input type="hidden" name="postId" value="<%= postId %>">
                <input type="hidden" name="action" value="<%= liked ? "unlike" : "like" %>">
                <button type="submit" class="like-button">
                    <%= liked ? "❤️" : "🤍" %> <%= post.getLikeCount() %>
                </button>
            </form>
        </div>
    </div>
<% } %>
</div>

<% } %>

</body>
</html>
