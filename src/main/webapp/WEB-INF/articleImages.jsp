<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Zdjęcia do artykułu</title>
</head>
<body>

    <form:form method="post" action="/add/articleImages" modelAttribute="articleImages">
        <form:hidden path="specificArticle"/>

        <label>
            Ścieżka do zdjęcia:<br>
            <form:input type="text" path="imgSrc"/>
        </label>
        <br>
        <label>
            Opis zdjęcia:<br>
            <form:input type="text" path="altImg"/>
        </label>
        <br>
        <label>
            <input type="submit" value="Przejdź dalej"/>
        </label>
        <label>
            <input type="reset" value="Usuń wartości">
        </label>
    </form:form>
</body>
</html>