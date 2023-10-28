<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <title>Login</title>
    <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
</head>
<body>

<c:if test="${error != null}">
  <div>
    Wrong email-password.
  </div>
</c:if>

<form:form method="POST" modelAttribute="loginForm" class="login-form" id="loginForm">

  <form:label path="email">Email</form:label>
  <form:input type="email" path="email" class="form-control" id="email" />
  <form:errors path="email" class="error-message" /><br>

  <form:label path="password">Password</form:label>
  <form:input type="password" path="password" class="form-control" id="password" />
  <form:errors path="password" class="error-message" /><br>

  <input type="submit" value="Login" class="btn btn-primary" />
</form:form>

</body>
</html>
