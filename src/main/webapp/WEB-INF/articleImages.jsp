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
<form action="/add/articleImages" method="post" class="image-form">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <c:forEach var="i" begin="1" end="10">
        <label>
            Podaj ścieżkę do zdjęcia ${i}
            <input type="text" name="imgSrc${i}"/>
            Podaj opis zdjęcia ${i}
            <input type="text" name="altImg${i}"/>
        </label>
    </c:forEach>

    <label>
        <input type="submit" value="Przejdź dalej"/>
        <input type="reset" value="Usuń wartości"/>
    </label>
</form>

<footer>
    <a href="/panel" class="button">Powrót do panelu</a>
    <a href="/logout" class="button">Logout</a>
</footer>

</body>
</html>