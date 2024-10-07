<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Ustawienia</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/settings.css">
</head>
<body>

<div class="container">
    <h1 class="page-title">Ustawienia</h1>

    <button class="button" onclick="window.location.href='/changePassword'">Zmień hasło</button>
    <button class="button" onclick="window.location.href='/changeEmail'">Zmień adres E-mail</button>

    <footer class="footer">
        <button class="button" onclick="window.location.href='/panel'">Powrót do panelu</button>
        <button class="button" onclick="window.location.href='/logout'">Logout</button>
    </footer>
</div>

</body>
</html>