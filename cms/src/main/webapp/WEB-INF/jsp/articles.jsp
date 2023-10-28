<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<html>
<head>
    <title>Articles</title>
    <link href="<c:url value="/css/styles.css" />" rel="stylesheet">

</head>

<body>

<div class="container">

    <c:forEach items="${articles.content}" var="article">

        <div class="row">

            <div class = "col">
                   <a href="${spring:mvcUrl('AC#article').arg(0, article.slug).build()}">
                           ${article.title}
                   </a>

            </div>

        </div>

    </c:forEach>

</div>

<c:if test="${totalPages > 1}">

    <ul class="pagination">
        <c:if test="${page > 2}">
            <li>
                <a href="${spring:mvcUrl('AC#list').arg(0, (1).intValue()).build()}">
                        ${1}
                </a>
                ${" ... "}
            </li>
        </c:if>

        <c:if test="${page > 1}">
            <li>
                <a href="${spring:mvcUrl('AC#list').arg(0, (page - 1).intValue()).build()}">
                    ${page-1}
                </a>
            </li>
        </c:if>
            <li>${page}</li>
        <c:if test="${page < totalPages}">
            <li>
                <a href="${spring:mvcUrl('AC#list').arg(0, (page + 1).intValue()).build()}">
                        ${page+1}
                </a>
            </li>
        </c:if>

        <c:if test="${page + 1 < totalPages}">
            ${" ... "}
            <li>
                <a href="${spring:mvcUrl('AC#list').arg(0, (totalPages).intValue()).build()}">
                        ${totalPages}
                </a>
            </li>
        </c:if>


    </ul>

</c:if>


<security:authorize access="hasAuthority('ADMIN')">
<a href="${spring:mvcUrl('AC#creator').build()}">Создать новую статью</a>
</security:authorize>

</body>
</html>
