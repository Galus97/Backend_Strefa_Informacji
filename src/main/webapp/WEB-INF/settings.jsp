<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Ustawienia</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/settings.css">
</head>
<body>

<h1 class="page-title">Ustawienia</h1>

<label>
    <a href="/changePassword">Zmień hasło</a>
</label>
<label>
    <a href="/changeEmail">Zmień adres E-mail</a>
</label>

<footer class="footer">
    <a href="/panel" class="button">Powrót do panelu</a>
    <a href="/logout" class="button">Logout</a>
</footer>

</body>
</html>