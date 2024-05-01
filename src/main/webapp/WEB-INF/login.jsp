<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Login form</title>

</head>
<body>

<form:form method="post" action="/login">


    <div>
        Podaj nazwę użytkownika: <br>
        <input name="username" />
    </div>
    <div>
        Podaj hasło:<br>
        <input type="password" name="password"/>
    </div>
    <div>
        <input type="submit" value="Zaloguj się">
    </div><br>
    <div>
        <input type="reset" value="Usuń wartości">
    </div>

</form:form>

</body>
</html>