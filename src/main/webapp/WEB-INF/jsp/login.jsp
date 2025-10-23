<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<%@ include file="/common/firstHeader.jsp" %>

<h1>Login</h1>

<main>

<c:if test="${not empty errorMsg}">
    <p style="color:red;">${errorMsg}</p>
</c:if>

<form action="LoginServlet" method="post" class="form-container">
    <div class="form-group">
        <label for="userId">UserId</label>
        <input type="text" id="userId" name="userId" required>
    </div>

    <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>
    </div>
<button type="submit" class="button">Login</button>
   
</form>

<a href="NewAduserServlet"><button type="button" class="button">NewAccount</button></a>

</main>
</body>
</html>
