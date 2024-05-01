<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Specific Article</title>
</head>
<body>

<form:form action="/add/specificArticle" method="post" modelAttribute="specificArticle">
    <form:hidden path="articleInformation"/>

    <label>
        Tytuł artykułu: <br>
        <form:input type="text" path="title"/>
    </label>
    <br>
    <label>
        Tekst artykułu <br>
        <form:textarea path="description"/>
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