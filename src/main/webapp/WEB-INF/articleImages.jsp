<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Zdjęcia do artykułu</title>
</head>
<body>

<h3>Dodaj zdjęcia do artykułu</h3>
<form action="/add/articleImages" method="post">
    <input type="hidden" name="specificArticleId" value="${specificArticleId}"/>
        <label>
            Podaj ścieżkę do zdjęcia 1 <input type="text" name="imgSrc1"/> <br>
            Podaj opis zdjęcia 2 <input type="text" name="altImg1"/>
        </label> <br>

    <label>
        Podaj ścieżkę do zdjęcia 3 <input type="text" name="imgSrc2"/> <br>
        Podaj opis zdjęcia 4 <input type="text" name="altImg2"/>
    </label> <br>
    <label>
        <input type="submit" value="Przejdź dalej"/>
        <input type="reset" value="Usuń wartości">
    </label>
</form>
</body>
</html>