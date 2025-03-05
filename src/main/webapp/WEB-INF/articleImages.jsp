<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Zdjęcia do artykułu</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/article.css">
</head>
<body>


<h3>Dodaj zdjęcia do artykułu</h3>
<form:form action="/add/articleImages" method="post" modelAttribute="form">
    <form:errors path="images" cssClass="error-message"/>

    <c:forEach items="${form.images}" varStatus="status">
        <div class="image-group">
            <form:label path="images[${status.index}].imgSrc">
                Ścieżka do zdjęcia ${status.index + 1}
            </form:label>
            <form:input path="images[${status.index}].imgSrc"/>
            <form:errors path="images[${status.index}].imgSrc" cssClass="field-error"/>

            <form:label path="images[${status.index}].altImg">
                Opis zdjęcia ${status.index + 1}
            </form:label>
            <form:input path="images[${status.index}].altImg"/>
            <form:errors path="images[${status.index}].altImg" cssClass="field-error"/>
        </div>
    </c:forEach>

    <label>
        <input type="submit" value="Przejdź dalej"/>
        <input type="reset" value="Usuń wartości"/>
    </label>
</form:form>

<footer>
    <a href="/panel" class="button">Powrót do panelu</a>
    <a href="/logout" class="button">Logout</a>
</footer>

</body>
</html>