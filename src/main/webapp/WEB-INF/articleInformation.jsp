<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Dodawanie artykułu</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/article.css">
</head>
<body>

<form:form action="/add/articleInformation" method="post" modelAttribute="articleInformation" class="article-form">
    <form:hidden path="employee"/>

    <label class="form-label">
        Tytuł artykułu:
        <form:input type="text" path="title" class="form-input"/>
    </label>

    <label class="form-label">
        Krótki opis artykułu:
        <form:textarea path="shortDescription" class="form-textarea"/>
    </label>

    <label class="form-label">
        Ważność artykułu (1-10):
        <form:input type="number" path="importance" min="1" max="10" class="form-input"/>
    </label>

    <label class="form-label">
        Link do zdjęcia:
        <form:input type="text" path="imgSrc" class="form-input"/>
    </label>

    <label class="form-label">
        Krótki opis zdjęcia:
        <form:input type="text" path="altImg" class="form-input"/>
    </label>

    <div class="form-actions">
        <input type="submit" value="Przejdź dalej" class="form-button"/>
        <input type="reset" value="Usuń wartości" class="form-button reset-button"/>
    </div>
</form:form>

</body>
</html>