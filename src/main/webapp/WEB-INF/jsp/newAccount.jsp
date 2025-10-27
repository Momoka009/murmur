<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<%@ include file="/common/firstHeader.jsp" %>
<h1>NewAccount</h1>

<% String errorMsg = (String) request.getAttribute("errorMsg"); %>
<% model.UsersBean user = (model.UsersBean) request.getAttribute("user"); %>

<% if (errorMsg != null) { %>
    <p style="color:red;"><%= errorMsg %></p>
<% } %>

<form action="NewAduserServlet" method="post" class="form-container">

  <div class="form-group">
    <label>NickName</label>
    <input type="text" name="nickname" value="<%= user != null ? user.getNickname() : "" %>" required>
<!--    この場合のvalue値はエラーが出て再表示する際に入力していた値を保持するため-->
  </div>

  <div class="form-group">
    <label>UserId</label>
    <input type="text" name="userId" value="<%= user != null ? user.getUserId() : "" %>" required>
  </div>

  <div class="form-group">
    <label>Password(4文字以上)</label>
    <input type="password" name="password" required>
  </div>

  <div class="form-group">
    <label>Password(確認用)</label>
    <input type="password" name="passwordCheck" required>
  </div>
<button type="submit" class="button">Sign up</button>
</form>

<a href="LoginServlet"><button type="button" class="button">Cancel</button></a>


</body>
</html>
