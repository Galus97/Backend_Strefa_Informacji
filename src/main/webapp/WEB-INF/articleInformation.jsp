<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Dodawanie artykułu</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/article.css">
    <script type="text/javascript">
        function clearError(field) {
            document.getElementById(field + '-error').innerHTML = '';
        }
    </script>
</head>
<body>

<form:form action="/add/articleInformation" method="post" modelAttribute="articleInformation" class="article-form">
    <form:hidden path="employee"/>

    <label class="form-label">
        Tytuł artykułu:
        <form:input type="text" path="title" class="form-input" oninput="clearError('title')"/>
        <form:errors path="title" cssClass="error-message" id="title-error"/>
    </label>

    <label class="form-label">
        Krótki opis artykułu:
        <form:textarea path="shortDescription" class="form-textarea" oninput="clearError('shortDescription')"/>
        <form:errors path="shortDescription" cssClass="error-message" id="shortDescription-error"/>
    </label>

    <label class="form-label">
        Ważność artykułu (1-10):
        <form:input type="number" path="importance" min="1" max="10" class="form-input" oninput="clearError('importance')"/>
        <form:errors path="importance" cssClass="error-message" id="importance-error"/>
    </label>

    <label class="form-label">
        Link do zdjęcia:
        <form:input type="text" path="imgSrc" class="form-input" oninput="clearError('imgSrc')"/>
        <form:errors path="imgSrc" cssClass="error-message" id="imgSrc-error"/>
    </label>

    <label class="form-label">
        Krótki opis zdjęcia:
        <form:input type="text" path="altImg" class="form-input" oninput="clearError('altImg')"/>
        <form:errors path="altImg" cssClass="error-message" id="altImg-error"/>
    </label>

    <div class="form-actions">
        <input type="submit" value="Przejdź dalej" class="form-button"/>
        <input type="reset" value="Usuń wartości" class="form-button reset-button"/>
    </div>
</form:form>

<footer>
    <a href="/panel" class="button">Powrót do panelu</a>
    <a href="/logout" class="button">Logout</a>
</footer>

</body>
</html>