<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login form</title>

</head>
<body>

<form:form method="post" action="/login">


    <label>
        Podaj nazwę użytkownika:
        <input name="username" />
    </label>
    <br>
    <label>
        Podaj hasło:
        <input type="password" name="password"/>
    </label>
    <br>
    <label>
        <input type="submit" value="Zaloguj się">
    </label>
    <br>
    <label>
        <input type="reset" value="Usuń wartości">
    </label>
    <br>
    <br>
    <label>
        <h3>Nie masz jeszcze konta</h3>
        <a href="/register">Załóż nowe konto</a>
    </label>

</form:form>

</body>
</html>