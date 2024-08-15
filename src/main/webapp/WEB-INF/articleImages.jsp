<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Zdjęcia do artykułu</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/article.css">
</head>
<body>

<h3>Dodaj zdjęcia do artykułu</h3>
<form action="/add/articleImages" method="post" class="image-form">
    <input type="hidden" name="specificArticleId" value="${specificArticleId}"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <label>
        Podaj ścieżkę do zdjęcia 1
        <input type="text" name="imgSrc1"/>
        Podaj opis zdjęcia 1
        <input type="text" name="altImg1"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 2
        <input type="text" name="imgSrc2"/>
        Podaj opis zdjęcia 2
        <input type="text" name="altImg2"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 3
        <input type="text" name="imgSrc3"/>
        Podaj opis zdjęcia 3
        <input type="text" name="altImg3"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 4
        <input type="text" name="imgSrc4"/>
        Podaj opis zdjęcia 4
        <input type="text" name="altImg4"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 5
        <input type="text" name="imgSrc5"/>
        Podaj opis zdjęcia 5
        <input type="text" name="altImg5"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 6
        <input type="text" name="imgSrc6"/>
        Podaj opis zdjęcia 6
        <input type="text" name="altImg6"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 7
        <input type="text" name="imgSrc7"/>
        Podaj opis zdjęcia 7
        <input type="text" name="altImg7"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 8
        <input type="text" name="imgSrc8"/>
        Podaj opis zdjęcia 8
        <input type="text" name="altImg8"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 9
        <input type="text" name="imgSrc9"/>
        Podaj opis zdjęcia 9
        <input type="text" name="altImg9"/>
    </label>

    <label>
        Podaj ścieżkę do zdjęcia 10
        <input type="text" name="imgSrc10"/>
        Podaj opis zdjęcia 10
        <input type="text" name="altImg10"/>
    </label>


    <label>
        <input type="submit" value="Przejdź dalej"/>
        <input type="reset" value="Usuń wartości"/>
    </label>
</form>

</body>
</html>