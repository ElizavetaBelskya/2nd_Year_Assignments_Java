<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Sign up</title>
    <meta charset="UTF-8">
    <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
</head>
<body>
<h2>Create an account</h2>
<h4>${message}</h4>

<form:form method="POST" modelAttribute="user">

    <form:label path="email">Email</form:label>
    <form:input type="email" path="email"/>
    <form:errors path="email" /><br>

    <form:label path="password">Password</form:label>
    <form:input type="password" path="password"/>
    <form:errors path="password" /><br>

    <input type="submit" value="Sign up" />
</form:form>


</body>
</html>
