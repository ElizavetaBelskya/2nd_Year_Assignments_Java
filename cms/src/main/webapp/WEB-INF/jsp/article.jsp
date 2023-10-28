<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
<head>
    <title>${article.title}</title>
    <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
</head>

<body>

<div class="article-container">
    <h1 class="article-title">${article.title}</h1>
    <div class="article-text">
        <p>
            ${article.text}
        </p>
    </div>

    <security:authorize access="hasAuthority('ADMIN')">
        <div class="article-actions">
            <form action="${spring:mvcUrl('AC#deleteArticle').arg(0, article.slug).build()}" method="POST">
                <button type="submit" class="delete-button">Удалить</button>
            </form>

            <form action="${spring:mvcUrl('AC#edit').arg(0, article.slug).build()}" method="GET">
                <button class="edit-button">Редактировать</button>
            </form>
        </div>
    </security:authorize>
</div>

</body>
</html>