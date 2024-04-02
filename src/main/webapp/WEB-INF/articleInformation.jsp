<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Dodawanie artykułu</title>
</head>
<body>

<form:form action="/add/articleInformation" method="post" modelAttribute="articleInformation">
    <label>
        Tytuł artykułu: <br>
        <form:input type="text" path="title"/>
    </label>
    <br>
    <label>
        Krótki opis artykułu: <br>
        <form:textarea path="shortDescription"/>
    </label>
    <br>
    <label>
        Ważność artykułu: <br>
        <form:input type="number" path="importance" min="1" max="10"/>
    </label>
    <br>
    <label>
        Link do zdjęcia: <br>
        <form:input type="text" path="imgSrc"/>
    </label>
    <br>
    <label>
        Krótki opis zdjęcia: <br>
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