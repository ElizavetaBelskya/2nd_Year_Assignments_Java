<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
  <title>Editor</title>
  <link href="<c:url value="/css/styles.css" />" rel="stylesheet">
  <link rel="stylesheet" href="<c:url value='/static/ckeditor5-build-classic/sample/css/sample.css' />"/>
  <script src="<c:url value='/static/ckeditor5-build-classic/ckeditor.js' />"></script>

</head>
<body>


<form:form method="POST" modelAttribute="article" class="article-form">
  <form:label path="title" class="form-label">Title</form:label>
  <form:input path="title" class="form-input"/>
  <form:errors path="title" class="form-error"/>

  <form:label path="text" class="form-label">Text</form:label>
  <form:textarea path="text" id="editor" name="content" rows="10" cols="80" class="form-textarea"/>
  <form:errors path="text" class="form-error"/>
  <button type="submit" class="edit-button">Редактировать</button>
</form:form>



<script>
  ClassicEditor
          .create( document.querySelector( '#editor' ) )
          .catch( error => {
            console.error( error );
          } );

</script>


</body>
</html>
