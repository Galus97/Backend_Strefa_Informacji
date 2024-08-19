<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Specific Article</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/article.css">
</head>
<body>

<form:form action="/add/specificArticle" method="post" modelAttribute="specificArticle" class="article-form">

    <label class="form-label">
        Tytuł artykułu:
        <form:input type="text" path="title" class="form-input"/>
    </label>

    <label class="form-label">
        Tekst artykułu:
        <form:textarea path="description" class="form-textarea"/>
    </label>

    <div class="form-actions">
        <input type="submit" value="Przejdź dalej" class="form-button"/>
        <input type="reset" value="Usuń wartości" class="form-button reset-button"/>
    </div>
</form:form>

</body>
</html>